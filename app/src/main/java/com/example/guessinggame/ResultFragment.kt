package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding
import com.example.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding : FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater,container,false).apply {
            val text = ResultFragmentArgs.fromBundle(requireArguments()).result
            wonLost.text = text
            context?.let {
                ivResult.setImageDrawable(ContextCompat.getDrawable(it,
                    if (text.contains("win")) R.drawable.winner else R.drawable.loser))
            }
            newGameButton.setOnClickListener{
                root.findNavController()
                    .navigate(R.id.action_resultFragment_to_gameFragment)
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}