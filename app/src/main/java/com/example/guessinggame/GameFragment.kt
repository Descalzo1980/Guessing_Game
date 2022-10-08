package com.example.guessinggame


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val words = listOf("S")
    private val secretWord = words.random().uppercase()
    private var secretWordDisplay = ""
    private var correctGuess = ""
    private var incorrectGuess = ""
    private var livesLeft = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        secretWordDisplay = deriveSecretWordDisplay()
        updateScreen()
        binding.guessButton.setOnClickListener {
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if (isWon() || isLost()) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = "You have $livesLeft lives left."
        binding.incorrectGuesses.text = "Incorrect guesses: $incorrectGuess"
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) = when (correctGuess.contains(str)) {
        true -> str
        false -> ""
    }

    private fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuess += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuess += guess
                livesLeft--
            }
        }
    }


    private fun isWon() = secretWord.equals(secretWordDisplay, true)

    private fun isLost() = livesLeft <= 0

    @SuppressLint("ResourceType")
    private fun wonLostMessage(): String {
        var message = if (isWon()) "You win," else "You lost,"
        message += " the secret word was $secretWord."
        return message
    }
}


