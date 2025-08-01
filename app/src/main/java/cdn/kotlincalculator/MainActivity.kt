package cdn.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cdn.kotlincalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastResult: String = ""
    private var isResultJustCalculated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Numbers
        binding.num0.setOnClickListener { appendVal("0", false) }
        binding.num1.setOnClickListener { appendVal("1", false) }
        binding.num2.setOnClickListener { appendVal("2", false) }
        binding.num3.setOnClickListener { appendVal("3", false) }
        binding.num4.setOnClickListener { appendVal("4", false) }
        binding.num5.setOnClickListener { appendVal("5", false) }
        binding.num6.setOnClickListener { appendVal("6", false) }
        binding.num7.setOnClickListener { appendVal("7", false) }
        binding.num8.setOnClickListener { appendVal("8", false) }
        binding.num9.setOnClickListener { appendVal("9", false) }
        binding.numDot.setOnClickListener { appendVal(".", false) }

        // Operators
        binding.clear.setOnClickListener { appendVal("", true) }
        binding.actionBack.setOnClickListener {
            val expression = binding.placeholder.text.toString()
            if (expression.isNotEmpty()) {
                binding.placeholder.text = expression.substring(0, expression.length - 1)
            }
        }
        binding.startBracket.setOnClickListener { appendVal(" ( ", false) }
        binding.closeBracket.setOnClickListener { appendVal(" ) ", false) }
        binding.actionDivide.setOnClickListener { appendVal(" / ", false) }
        binding.actionMultiply.setOnClickListener { appendVal(" × ", false) }
        binding.actionMinus.setOnClickListener { appendVal(" - ", false) }
        binding.actionAdd.setOnClickListener { appendVal(" + ", false) }


        binding.actionBack.setOnClickListener {
            val expression = binding.placeholder.text.toString()
            if (expression.isNotEmpty()) {
                binding.placeholder.text = expression.substring(0, expression.length - 1)
            }
        }

        binding.actionEquals.setOnClickListener {
            try {
                val rawInput = binding.placeholder.text.toString()
                val expressionStr = rawInput.replace("×", "*")
                val expression = ExpressionBuilder(expressionStr).build()
                val result = expression.evaluate()

                val longResult = result.toLong()
                val resultStr = if (result == longResult.toDouble()) {
                    longResult.toString()
                } else {
                    result.toString()
                }

                binding.answer.text = resultStr
                lastResult = resultStr
                isResultJustCalculated = true

            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                Log.d("EXCEPTION", "Message: ${e.message}")
            }
        }


        binding.actionEquals.setOnClickListener {
            try {
                // Ganti 'x' jadi '*' agar bisa dihitung
                val rawInput = binding.placeholder.text.toString()
                val expressionStr = rawInput.replace("×", "*")
                val expression = ExpressionBuilder(expressionStr).build()
                val result = expression.evaluate()

                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    binding.answer.text = longResult.toString()
                } else {
                    binding.answer.text = result.toString()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                Log.d("EXCEPTION", "Message: ${e.message}")
            }
        }
    }



    private fun appendVal(string: String, isClear: Boolean) {
        if (isClear) {
            binding.placeholder.text = ""
            binding.answer.text = ""
            isResultJustCalculated = false
        } else {
            if (isResultJustCalculated) {
                // Jika tombol operator ditekan setelah hasil, lanjut dari hasil
                if (string.trim() in listOf("+", "-", "×", "/")) {
                    binding.placeholder.text = "$lastResult $string "
                } else {
                    // Jika bukan operator (misalnya angka), mulai dari nol lagi
                    binding.placeholder.text = string
                }
                isResultJustCalculated = false
            } else {
                binding.placeholder.append(string)
            }
        }
    }



}

