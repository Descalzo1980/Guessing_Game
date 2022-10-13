package com.example.guessinggame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    private val words = listOf("Strong","Dist","Keep","Port")
    private val secretWord = words.random().uppercase()
    val secretWordDisplay = MutableLiveData<String>()
    private var correctGuesses = ""
    val incorrectGuesses = MutableLiveData("")
    val livesLeft = MutableLiveData(8)

    init {
        secretWordDisplay.value = deriveSecretWordDisplay()
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
                secretWordDisplay.value =deriveSecretWordDisplay()
            }else{
                incorrectGuesses.value += guess
                livesLeft.value = livesLeft.value?.minus(1)
            }
        }
    }

    fun isWin() = secretWord.equals(secretWordDisplay.value,true)

    fun isLost() = (livesLeft.value ?: 0) <= 0

    fun wonLostMessage() : String {
        var message = if (isWin()) "You win," else "You lost,"
        message += " the secret word was $secretWord."
        return message
    }
}