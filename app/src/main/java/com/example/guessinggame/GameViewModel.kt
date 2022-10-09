package com.example.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    private val words = listOf("Strong","Dist","Keep","Port")
    private val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 5

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay() : String{
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) = when (correctGuesses.contains(str)){
        true -> str
        false -> ""
    }

    fun makeGuess(guess: String){
        if (guess.length == 1){
            if (secretWord.contains(guess)){
                correctGuesses +=guess
                secretWordDisplay =deriveSecretWordDisplay()
            }else{
                incorrectGuesses += guess
                livesLeft--
            }
        }
    }

    fun isWin() = secretWord.equals(secretWordDisplay,true)

    fun isLost() = livesLeft <= 0

    fun wonLostMessage() : String {
        var message = if (isWin()) "You win," else "You lost,"
        message += " the secret word was $secretWord."
        return message
    }

}