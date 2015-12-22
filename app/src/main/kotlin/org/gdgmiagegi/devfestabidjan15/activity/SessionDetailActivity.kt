package org.gdgmiagegi.devfestabidjan15.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.WindowManager

import org.gdgmiagegi.devfestabidjan15.R

class SessionDetailActivity : AppCompatActivity() {
        companion object{
            val EXTRA_SCHEDULE = "org.gdgmiagegi.devfestabidjan15.SessionDetail"
        }
    var mActionBarToolbar :Toolbar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldBeFloatingWindow = shouldBeFloatingWindow()
        if (shouldBeFloatingWindow) {
            setupFloatingWindow(R.dimen.session_details_floating_width,
                    R.dimen.session_details_floating_height, 1f, 0.4f);
        }
        setContentView(R.layout.activity_session_detail)
        val toolbar = getActionBarToolbar()
        setSupportActionBar(toolbar)

        toolbar.navigationIcon = if(shouldBeFloatingWindow)resources.getDrawable(R.drawable.ic_ab_close) else resources.getDrawable(R.drawable.ic_up)
        toolbar.setNavigationContentDescription(R.string.close_and_go_back)
        toolbar.setNavigationOnClickListener {
                finish()
        }

    }

    private fun  getActionBarToolbar() :Toolbar{
        if (mActionBarToolbar == null) {
            mActionBarToolbar = findViewById(R.id.toolbar_actionbar) as Toolbar
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar?.setNavigationContentDescription(getResources().getString(R.string
                        .navdrawer_description_a11y));
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar as Toolbar
    }

    /**
     * Configure this Activity as a floating window, with the given {@code width}, {@code height}
     * and {@code alpha}, and dimming the background with the given {@code dim} value.
     */
    private  fun setupFloatingWindow( width:Int, height:Int, alpha:Float,  dim:Float) {
        val params = window.attributes
        params.width = getResources().getDimensionPixelSize(width)
        params.height = getResources().getDimensionPixelSize(height)
        params.alpha = alpha
        params.dimAmount = dim
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        getWindow().setAttributes(params);
    }

    /**
     * Returns true if the theme sets the {@code R.attr.isFloatingWindow} flag to true.
     */
    private fun shouldBeFloatingWindow():Boolean {
       val theme = getTheme()
       val floatingWindowFlag =  TypedValue()

        // Check isFloatingWindow flag is defined in theme.
        if (theme == null || theme.resolveAttribute(R.attr.isFloatingWindow, floatingWindowFlag, true)) {
            return false;
        }

        return (floatingWindowFlag.data != 0);
    }

}
