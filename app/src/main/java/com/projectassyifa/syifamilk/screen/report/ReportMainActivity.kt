package com.projectassyifa.syifamilk.screen.report

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.projectassyifa.syifamilk.R
import kotlinx.android.synthetic.main.activity_report_main.*

class ReportMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_main)

        // Tabs Customization
        tab_layout.setSelectedTabIndicatorColor(Color.WHITE)
        tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.keduax))
        tab_layout.tabTextColors = ContextCompat.getColorStateList(this, android.R.color.white)

         val numberOfTabs = 2

        // Set Tabs in the center
        //tab_layout.tabGravity = TabLayout.GRAVITY_CENTER

        // Show all Tabs in screen
        tab_layout.tabMode = TabLayout.MODE_FIXED

        // Scroll to see all Tabs
        //tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

        // Set Tab icons next to the text, instead of above the text
        tab_layout.isInlineLabel = true

        // Set the ViewPager Adapter
        val adapter = AdapterScreenReport(supportFragmentManager, lifecycle, numberOfTabs)
        tabs_viewpager.adapter = adapter

        // Enable Swipe
        tabs_viewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Transaksi"
                    tab.setIcon(R.drawable.ic_tr)
                }
                1 -> {
                    tab.text = "Pembayaran"
                    tab.setIcon(R.drawable.ic_pay)

                }

            }
            // Change color of the icons
            tab.icon?.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE,
                    BlendModeCompat.SRC_ATOP
                )
        }.attach()


        setCustomTabTitles()

    }

    private fun setCustomTabTitles() {
        val vg = tab_layout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup

            val tabChildCount = vgTab.childCount

            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {

                    // Change Font and Size
                    tabViewChild.typeface = Typeface.DEFAULT_BOLD
//                    val font = ResourcesCompat.getFont(this, R.font.myFont)
//                    tabViewChild.typeface = font
//                    tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
                }
            }
        }
    }
}