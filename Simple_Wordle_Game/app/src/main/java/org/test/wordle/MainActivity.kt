package org.test.wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // get new word before app creation
    val newWord = FourLetterWordList.getRandomFourLetterWord()
    var guess = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Parameters / Fields:
         *   wordToGuess : String - the target word the user is trying to guess
         *   guess : String - what the user entered as their guess
         *
         * Returns a String of 'O', '+', and 'X', where:
         *   'O' represents the right letter in the right place
         *   '+' represents the right letter in the wrong place
         *   'X' represents a letter not in the target word
         */
        fun checkGuess(guess: String) : String
        {
            var result = ""
            for (i in 0..3) {
                if (guess[i] == newWord[i])
                {
                    result += "O"
                }
                else if (guess[i] in newWord)
                {
                    result += "+"
                }
                else
                {
                    result += "X"
                }
            }
            return result
        }

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        setContentView(R.layout.activity_main)

        /********************** VARIABLE DECLARATIONS ********************/
        // set variables for first guess
        val firstGuess = findViewById<TextView>(R.id.firstGuess)
        val firstCheck = findViewById<TextView>(R.id.firstCheck)
        val firstCheckResult = findViewById<TextView>(R.id.firstCheckResult)

        // set variables for second guess
        val second = findViewById<TextView>(R.id.second)
        val secondGuess = findViewById<TextView>(R.id.secondGuess)
        val secondCheck = findViewById<TextView>(R.id.secondCheck)
        val secondCheckResult = findViewById<TextView>(R.id.secondCheckResult)

        // set variables for third guess
        val third = findViewById<TextView>(R.id.third)
        val thirdGuess = findViewById<TextView>(R.id.thirdGuess)
        val thirdCheck = findViewById<TextView>(R.id.thirdCheck)
        val thirdCheckResult = findViewById<TextView>(R.id.thirdCheckResult)

        // get four letter word and store in answer variable
        val answer = findViewById<TextView>(R.id.answer)
        answer.text = newWord

        // get user input
        val editText = findViewById<EditText>(R.id.answerInput)

        // assign our button
        val guessButton = findViewById<Button>(R.id.submit)

        /********************** END VARIABLE DECLARATIONS ********************/
        guessButton.setOnClickListener {
            guess++
            it.hideKeyboard()
            // won't change state once answer is visible
            // occurs when user makes the right guess or guess == 4
            if (answer.visibility != View.VISIBLE)
            {
                // user's first guess
                if (guess == 1)
                {
                    val userInput = editText.text.toString()
                    editText.text.clear()
                    firstGuess.text = userInput.toString().uppercase()
                    firstCheckResult.text = checkGuess(firstGuess.text.toString())
                    if(firstCheckResult.text == "OOOO")
                    {
                        Toast.makeText(it.context, "CONGRATULATIONS!! " +
                                "YOU ARE GREATNESS PERSONIFIED!!!", Toast.LENGTH_LONG).show()
                        answer.visibility = View.VISIBLE
                        guessButton.text = "RESTART!"
                    }
                    else
                    {
                        Toast.makeText(it.context, "GREATNESS DENIED, " +
                                "TWO GUESSES LEFT", Toast.LENGTH_LONG).show()
                        firstCheck.visibility = View.VISIBLE
                        firstCheckResult.visibility = View.VISIBLE
                        second.visibility = View.VISIBLE
                        secondGuess.visibility = View.VISIBLE
                    }
                }
                // user's second guess
                if (guess == 2)
                {
                    var userInput = editText.text.toString()
                    editText.text.clear()
                    secondGuess.text = userInput.toString().uppercase()
                    secondCheckResult.text = checkGuess(secondGuess.text.toString())
                    if(secondCheckResult.text == "OOOO")
                    {
                        Toast.makeText(it.context, "CONGRATULATIONS!, " +
                                "YOU'RE ALRIGHT KID.", Toast.LENGTH_LONG).show()
                        answer.visibility = View.VISIBLE
                        guessButton.text = "RESTART!"
                    }
                    else
                    {
                        Toast.makeText(it.context, "IT'S OKAY. " +
                                "ONE GUESS LEFT", Toast.LENGTH_LONG).show()
                        secondCheck.visibility = View.VISIBLE
                        secondCheckResult.visibility = View.VISIBLE
                        third.visibility = View.VISIBLE
                        thirdGuess.visibility = View.VISIBLE
                    }
                }
                // user's last guess
                if (guess == 3)
                {
                    var userInput = editText.text.toString()
                    editText.text.clear()
                    thirdGuess.text = userInput.toString().uppercase()
                    thirdCheckResult.text = checkGuess(thirdGuess.text.toString())
                    if (thirdCheckResult.text == "OOOO")
                    {
                        Toast.makeText(
                            it.context, "YAY. " +
                                    "YOU DIDN'T FAIL.", Toast.LENGTH_LONG
                        ).show()
                        answer.visibility = View.VISIBLE
                        guessButton.text = "RESTART!"
                    }
                    else
                    {
                        Toast.makeText(
                            it.context, "TERRIBLE. " +
                                    "YOU'VE SNATCHED DEFEAT FROM THE JAWS OF VICTORY",
                            Toast.LENGTH_LONG
                        ).show()
                        thirdCheck.visibility = View.VISIBLE
                        thirdCheckResult.visibility = View.VISIBLE
                        answer.visibility = View.VISIBLE

                        // give them the option to restart
                        guessButton.text = "RESTART!"
                        guess = 0
                    }
                }
                // restart the activity if player chooses to restart hit
                if(guessButton.text == "RESTART!")
                {
                    guessButton.setOnClickListener {
                        recreate()
                    }
                }
            }
        }
    }
}