package com.example.guessinggame

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val words = listOf("Strong","Dist","Keep","Port")
    private val secretWord = words.random().uppercase()
    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay : LiveData<String>
    get() = _secretWordDisplay
    private var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData("")
    val incorrectGuesses : LiveData<String>
    get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData(8)
    val livesLeft: LiveData<Int>
    get() = _livesLeft

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
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
                _secretWordDisplay.value =deriveSecretWordDisplay()
            }else{
                _incorrectGuesses.value += guess
                _livesLeft.value = livesLeft.value?.minus(1)
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