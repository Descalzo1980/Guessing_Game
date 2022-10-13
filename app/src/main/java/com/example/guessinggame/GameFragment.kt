package com.example.guessinggame


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[(GameViewModel::class.java)]
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer {
                newValue -> binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
        })
        viewModel.livesLeft.observe(viewLifecycleOwner, Observer {
                newValue -> binding.lives.text = "You have $newValue lives left"
        })
        viewModel.secretWordDisplay.observe(viewLifecycleOwner) { newValue ->
            binding.word.text = newValue
        }
        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            if (viewModel.isWin() || viewModel.isLost()) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


