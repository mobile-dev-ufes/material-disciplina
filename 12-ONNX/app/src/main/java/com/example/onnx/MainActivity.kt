package com.example.onnx

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.FloatBuffer

class MainActivity : AppCompatActivity() {

    private lateinit var imgSelected: ImageView
    private lateinit var txtResult: TextView
    private lateinit var selectedBitmap: Bitmap


    // Atributos para utilização o ONNX
    private lateinit var env: OrtEnvironment
    private lateinit var session: OrtSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgSelected = findViewById(R.id.imgSelected)
        txtResult = findViewById(R.id.txtResult)

        val btnSelect = findViewById<Button>(R.id.btnSelect)
        val btnRun = findViewById<Button>(R.id.btnRun)

        initOrt()

        // Seleciona uma imagem do Album de fotos
        val pickImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                imgSelected.setImageBitmap(selectedBitmap)
            }
        }

        btnSelect.setOnClickListener {
            pickImage.launch("image/*")
        }

        btnRun.setOnClickListener {
            runInference()
        }

    }

    /**
     * Inicializa o ambiente de execução do ONNX Runtime e carrega o modelo de IA.
     *
     * Este método:
     * - Cria uma instância do OrtEnvironment, que representa o contexto global
     *   do ONNX Runtime no aplicativo
     * - Carrega o arquivo do modelo ONNX a partir da pasta assets
     * - Cria uma OrtSession, responsável por executar inferências com o modelo
     *
     * Este método deve ser chamado apenas uma vez durante o ciclo de vida da Activity,
     * pois a criação repetida de sessões ONNX é custosa em termos de desempenho.
     */
    private fun initOrt() {
        env = OrtEnvironment.getEnvironment()
        val modelBytes = assets.open("model.onnx").readBytes()
        session = env.createSession(modelBytes)
    }


    /**
     * Realiza o pré-processamento da imagem para o formato esperado pelo modelo ONNX.
     *
     * Este método executa as seguintes etapas:
     * - Redimensiona a imagem para 224x224 pixels
     * - Extrai os canais de cor RGB de cada pixel
     * - Normaliza os valores de cada canal para o intervalo [0, 1]
     * - Reorganiza os dados no formato NCHW (Channels First), conforme esperado
     *   por modelos treinados em frameworks como PyTorch
     *
     * O resultado é um array de floats pronto para ser convertido em um tensor ONNX.
     *
     * @param bitmap Imagem original selecionada pelo usuário
     * @return Array de floats contendo os dados da imagem pré-processada
     */
    private fun preprocess(bitmap: Bitmap): FloatArray {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val input = FloatArray(3 * 224 * 224)
        var rIndex = 0
        var gIndex = 224 * 224
        var bIndex = 2 * 224 * 224
        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = resized.getPixel(x, y)
                input[rIndex++] = ((pixel shr 16 and 0xFF) / 255f)
                input[gIndex++] = ((pixel shr 8 and 0xFF) / 255f)
                input[bIndex++] = ((pixel and 0xFF) / 255f)
            }
        }
        return input
    }

    /**
     * Executa a inferência do modelo ONNX utilizando a imagem selecionada pelo usuário.
     *
     * Este método:
     * - Chama o pré-processamento da imagem
     * - Cria um tensor de entrada com shape compatível com o modelo (1, 3, 224, 224)
     * - Executa a inferência utilizando a OrtSession
     * - Obtém o vetor de saída do modelo
     * - Identifica a classe com maior probabilidade (argmax)
     * - Exibe o resultado na interface do usuário
     *
     * Toda a inferência é executada localmente no dispositivo, sem uso de rede,
     * caracterizando um modelo de IA embarcada.
     *
     * Observação: em aplicações reais, é desejado que este método seja executado fora da
     * UI Thread (por exemplo, usando Coroutines) para evitar bloqueios da interface.
     */
    private fun runInference() {

        val inputData = preprocess(selectedBitmap)
        val shape = longArrayOf(1, 3, 224, 224)


        val tensor = OnnxTensor.createTensor(
            env,
            FloatBuffer.wrap(inputData),
            shape
        )

        val result = session.run(
            mapOf(session.inputNames.first() to tensor)
        )

        val output = result[0].value as Array<FloatArray>
        val prediction = output[0]

        val maxIndex = prediction.indices.maxByOrNull { prediction[it] } ?: -1
        txtResult.text = "Classe prevista: $maxIndex"
    }


}