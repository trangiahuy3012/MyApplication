package com.example.myapplication.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOnboardingBinding
import com.example.myapplication.screens.onboading.OnboardingItemsAdapter
import com.example.myapplication.screens.onboarding.OnboardingItem

/**
 * A simple [Fragment] subclass.
 * Use the [OnboardingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var onboadingItemsAdapter: OnboardingItemsAdapter
    private lateinit var textViewTitleArray:Array<String>
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val root  = binding.root
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)

        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OnboardingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun setOnboardingItems()
    {
        textViewTitleArray = resources.getStringArray(R.array.text_view_title)
        onboadingItemsAdapter = OnboardingItemsAdapter(listOf(
            OnboardingItem(R.drawable.n1, textViewTitleArray[0]),
            OnboardingItem(R.drawable.charles_deluvio_d_vdqmtfaau_unsplash__1__removebg_preview__1__1, textViewTitleArray[1]),
            OnboardingItem(R.drawable.n2, textViewTitleArray[2])
        ))
        val onboardingViewPager = binding.viewPager2
        //
        var index = 0
        var previousState = ViewPager2.SCROLL_STATE_IDLE

        onboardingViewPager.adapter = onboadingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                index = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if ((index >= onboadingItemsAdapter.itemCount - 1)
                    && previousState == ViewPager2.SCROLL_STATE_DRAGGING
                    && state == ViewPager2.SCROLL_STATE_IDLE
                )
                    navigateToWelcomeActivity()

                previousState = state
            }
        })
//        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.imageNext.setOnClickListener {
            if(onboardingViewPager.currentItem + 1 < onboadingItemsAdapter.itemCount)
            {
                onboardingViewPager.currentItem += 1
            }
            else
                navigateToWelcomeActivity()
        }
    }

    private fun navigateToWelcomeActivity() {
        val controller = findNavController()
        controller.navigate(R.id.action_onboardingFragment_to_welcomeFragment)
    }

    private fun setupIndicators()
    {
        indicatorContainer = binding.indicatorsContainer
        val indicators = arrayOfNulls<ImageView>(onboadingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(binding.root.context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(binding.root.context,
                        R.drawable.ellipse_88
                    ))
                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position:Int)
    {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount)
        {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(binding.root.context,
                    R.drawable.group_17896
                ))
            }
            else
                imageView.setImageDrawable(ContextCompat.getDrawable(binding.root.context,
                    R.drawable.group_17896
                ))
        }
    }
}