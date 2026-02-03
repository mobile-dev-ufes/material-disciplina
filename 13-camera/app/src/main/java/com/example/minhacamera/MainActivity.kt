package com.example.minhacamera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var imageCapture: ImageCapture


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        previewView = findViewById(R.id.previewView)
        val btnCapture = findViewById<Button>(R.id.btnCapture)


        if (hasCameraPermission()) {
            startCamera()
        } else {
            requestCameraPermission()
        }


        btnCapture.setOnClickListener {
            capturePhoto()
        }

    }

    /*
        Verifica se o aplicativo já possui a permissão de uso da câmera concedida pelo sistema Android.
        O resultado deste método deve ser usado para decidir se a câmera pode ser
        inicializada imediatamente ou se é necessário solicitar a permissão ao usuário
        antes de acessar o hardware
     */
    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /*
        Solicita ao usuário a permissão de uso da câmera em tempo de execução.
        O requestCode (101) é utilizado para identificar esta solicitação específica
        quando o resultado for recebido.

        Este método NÃO garante que a permissão será concedida; ele apenas inicia
        o processo de solicitação. A inicialização da câmera deve ocorrer somente
        após a confirmação de que a permissão foi concedida.
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            101
        )
    }

    /***
     * startCamera() inicializa a câmera do dispositivo usando CameraX,
     * conecta o fluxo de imagem ao preview da tela e prepara o app para capturar fotos,
     * respeitando automaticamente o ciclo de vida da Activity.
     */
    private fun startCamera() {

        /*
        Gerenciador central da câmera no CameraX
           Basicamente ele faz:
            - Controla acesso ao hardware
            - Garante compatibilidade
            - Gerencia múltiplos casos de uso

           Ele retorna um Future, pois a inicialização pode demorar.
         */
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)


        /*
            Este é um Listener Assíncrono. A câmera não é aberta imediatamente.
            O sistema:
                -Inicializa drivers
                - Verifica hardware
                - Ajusta compatibilidade
            Quando estiver pronta, esse listener é chamado.
         */
        cameraProviderFuture.addListener({

            // Aqui você obtém o objeto real que controla a câmera.
            // Esse método só é seguro dentro do listener.
            val cameraProvider = cameraProviderFuture.get()

            // Recebe frames da câmera e Envia para uma Surface
            // PreviewView é a View do layout XML. Basicamente, mostra a imagem na tela
            // Gerencia rotação, escala, aspect ratio ...
            // Sem isso, a câmera funcionaria, mas nada apareceria na tela.
            // Isso é conhecido como UseCase (ou seja, pode ou não existir)
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // Este é outro UseCase
            // Responsável por:
            // - Capturar fotos, Codificar JPEG e Salvar arquivo
            // Modos de captura
            // - MINIMIZE_LATENCY: foto rápida
            // - MAXIMIZE_QUALITY: melhor qualidade, mais lenta
            // Obviamente, a escolha depende do app.
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            // Aqui você define: câmera traseira ou frontal (CameraX abstrai completamente o hardware)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Limpar vínculos anteriores
            // Evita: Câmera duplicada, Vazamento de recursos, Crash por múltiplos binds, etc
            // Sempre faça isso antes de um novo bind.
            cameraProvider.unbindAll()

            // Vinculando tudo ao ciclo de vida
            // Basicamente:
            // - Abre a câmera
            // - Ativa os use cases
            // - Conecta preview + captura
            // - Associa tudo ao LifecycleOwner
            // Como this é uma Activity, o que acontece é:
            // 1) Ao abrir, a câmera liga
            // 2) Ao pausar, a câmera pausa
            // 3) Ao fechar, a câmera libera hardware
            // Zero código manual de lifecycle, tudo gerenciado pelo CameraX
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture
            )
        }, ContextCompat.getMainExecutor(this))
    }

    /***
     * usa o use case ImageCapture do CameraX para capturar um frame da câmera,
     * codificá-lo como imagem (JPEG) e salvá-lo em um destino definido,
     * notificando o app quando a operação termina ou falha.
     */
    private fun capturePhoto() {

        /*
            Define onde a imagem será salva. Cria um nome único baseado no timestamp.
            Como usa o externalMediaDirs.first(), usa um diretório externo privado do app.
            Por isso, não requer permissão extra. O dado pode ser removido ao desinstalar o app.
            O arquivo não é criado ainda, apenas definido.
         */
        val file = File(
            externalMediaDirs.first(),
            "IMG_${System.currentTimeMillis()}.jpg"
        )

        /*
            É um objeto de configuração que diz ao CameraX: onde salvar a imagem,
            como lidar com metadados, Qual URI/arquivo usar.
            Aqui estamos usando um File, mas poderia ser um MediaStore (galeria)
         */
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        /*
            É o use case ImageCapture que foi criado em startCamera() (se não fosse = crash)
            Já está ligado ao lifecycle.
         */
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this), // define em qual thread vai executar o callback. Neste caso é Main
            object : ImageCapture.OnImageSavedCallback {

                /*
                    Chamado quando: a foto foi capturada, JPEG foi gerado e o Arquivo foi salvo com sucesso
                    A partir dele você pode: mostrar uma mensagem, abrir a imagem, enviar para uma IA, compartilahr, etc
                 */
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        applicationContext,
                        "Foto salva em: ${file.absolutePath}",
                        Toast.LENGTH_LONG
                    ).show()
                }


                /*
                    Chamado se houver falha (neste caso, só mostra a pilha de erros lol)
                 */
                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )
    }
}

