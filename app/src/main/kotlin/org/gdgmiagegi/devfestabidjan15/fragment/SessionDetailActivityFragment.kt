package org.gdgmiagegi.devfestabidjan15.fragment

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.samples.apps.iosched.SessionDetailConstants
import com.google.samples.apps.iosched.ui.widget.BezelImageView
import com.google.samples.apps.iosched.ui.widget.CheckableFloatingActionButton
import com.google.samples.apps.iosched.ui.widget.ObservableScrollView
import com.google.samples.apps.iosched.util.ImageLoader
import com.google.samples.apps.iosched.util.LUtils
import com.google.samples.apps.iosched.util.LogUtils
import com.google.samples.apps.iosched.util.UIUtills

import org.gdgmiagegi.devfestabidjan15.R
import org.gdgmiagegi.devfestabidjan15.activity.SessionDetailActivity
import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem
import org.gdgmiagegi.devfestabidjan15.model.Session
import org.gdgmiagegi.devfestabidjan15.model.Speaker
import org.gdgmiagegi.devfestabidjan15.util.extension.LIVE_DAY
import org.gdgmiagegi.devfestabidjan15.util.extension.isLive
import org.gdgmiagegi.devfestabidjan15.util.extension.live
import org.jetbrains.anko.find
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class SessionDetailActivityFragment : Fragment(), ObservableScrollView.Callbacks {



    companion object {

        private val TAG = "SessionDetailActivityFragment"


        private val PHOTO_ASPECT_RATIO = 1.7777777f
    }
    lateinit  var sessionTitleView : TextView
    lateinit  var sessionSubTitleView : TextView
    lateinit  var sessionTags : LinearLayout
    lateinit var sessionAbstract:TextView
    lateinit var sessionDescription :TextView
    lateinit var speakerTextView:TextView
    lateinit var timehintView: TextView

    private var mAddScheduleButtonContainer: View? = null
    private var mAddScheduleButton: CheckableFloatingActionButton? = null

    private var mAddScheduleButtonContainerHeightPixels: Int = 0

    private var mScrollViewChild: View? = null

    private var mTitle: TextView? = null

    private var mSubtitle: TextView? = null

    private var mScrollView: ObservableScrollView? = null

    private var mAbstract: TextView? = null

    private var mLiveStreamVideocamIconAndText: TextView? = null

    private var mLiveStreamPlayIconAndText: TextView? = null

    private var mTags: LinearLayout? = null

    private var mTagsContainer: ViewGroup? = null

    private var mRequirements: TextView? = null

    private var mHeaderBox: View? = null

    private var mDetailsContainer: View? = null

    private var mPhotoHeightPixels: Int = 0

    private var mHeaderHeightPixels: Int = 0

    private var mHasPhoto: Boolean = false

    private var mPhotoViewContainer: View? = null

    private var mPhotoView: ImageView? = null

    private var mMaxHeaderElevation: Float = 0.toFloat()

    private var mFABElevation: Float = 0.toFloat()

    private var mSpeakersImageLoader: ImageLoader? = null
    private var mNoPlaceholderImageLoader: ImageLoader? = null

    private var mTimeHintUpdaterRunnable: Runnable? = null


    private var mLUtils: LUtils? = null

    private var mHandler: Handler? = null


    /*override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_session_detail, container, false)
         view?.let {
          sessionTitleView = it.find<TextView>(R.id.session_title)
             sessionSubTitleView = it.find<TextView>(R.id.session_subtitle)
             sessionAbstract = it.find<TextView>(R.id.session_abstract)
             sessionTags = it.find<LinearLayout>(R.id.session_tags)
             sessionDescription = it.find<TextView>(R.id.session_requirements)
             speakerTextView = it.find<TextView>(R.id.session_speakers_header)
             timehintView = it.find<TextView>(R.id.time_hint)

        }
        val schedule = activity.intent.extras.getSerializable(SessionDetailActivity.EXTRA_SCHEDULE) as ScheduleItem

        schedule?.let {
            timehintView.text = "${it.startTime} - ${it.endTime}"
            it.session?.let{
                sessionTitleView.text = it.title
                sessionDescription.text = it.description
                val tags = StringBuilder()
                it.tags?.forEach {
                    tags.append(it+" ")
                }
                val tagView = TextView(activity)
                tagView.text = tags.toString()
                sessionTags.addView(tagView)
                val speakers = StringBuilder("Speakers ")
                it.speakers?.forEach {
                    speakers.append(it.name+" ")
                }
                speakerTextView.text = speakers.toString()
            }
        }

        return view
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      return  inflater.inflate(R.layout.fragment_session_detail, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLUtils = LUtils.getInstance(activity as AppCompatActivity)
        mHandler = Handler()
        initViews()
        initViewListeners()
    }
    private fun initViews() {
        mFABElevation = resources.getDimensionPixelSize(R.dimen.fab_elevation).toFloat()
        mMaxHeaderElevation = resources.getDimensionPixelSize(
                R.dimen.session_detail_max_header_elevation).toFloat()

        mScrollView = activity.findViewById(R.id.scroll_view) as ObservableScrollView
        mScrollView!!.addCallbacks(this)
        val vto = mScrollView!!.viewTreeObserver
        if (vto.isAlive) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener)
        }

        mScrollViewChild = activity.findViewById(R.id.scroll_view_child)
        mScrollViewChild!!.visibility = View.INVISIBLE

        mDetailsContainer = activity.findViewById(R.id.details_container)
        mHeaderBox = activity.findViewById(R.id.header_session)
        mTitle = activity.findViewById(R.id.session_title) as TextView
        mSubtitle = activity.findViewById(R.id.session_subtitle) as TextView
        mPhotoViewContainer = activity.findViewById(R.id.session_photo_container)
        mPhotoView = activity.findViewById(R.id.session_photo) as ImageView

        mAbstract = activity.findViewById(R.id.session_abstract) as TextView




        //Find view that shows a Videocam icon if the session is being live streamed.
        mLiveStreamVideocamIconAndText = activity.findViewById(
                R.id.live_stream_videocam_icon_and_text) as TextView

        // Find view that shows a play button and some text for the user to watch the session live stream.
        mLiveStreamPlayIconAndText = activity.findViewById(
                R.id.live_stream_play_icon_and_text) as TextView

        mRequirements = activity.findViewById(R.id.session_requirements) as TextView
        mTags = activity.findViewById(R.id.session_tags) as LinearLayout
        mTagsContainer = activity.findViewById(R.id.session_tags_container) as ViewGroup

        ViewCompat.setTransitionName(mPhotoView, SessionDetailConstants.TRANSITION_NAME_PHOTO)

        mAddScheduleButtonContainer = activity.findViewById(R.id.add_schedule_button_container)
        mAddScheduleButton = activity.findViewById(R.id.add_schedule_button) as CheckableFloatingActionButton

        mNoPlaceholderImageLoader = ImageLoader(context)
        mSpeakersImageLoader = ImageLoader(context, R.drawable.person_image_empty)
    }
    private fun initViewListeners() {
        mAddScheduleButton?.setOnClickListener { view ->
            val starred = !(view as CheckableFloatingActionButton).isChecked
            //showStarred(starred, true)
            if (starred) {
               // sendUserAction(SessionDetailUserActionEnum.STAR, null)
            } else {
                //sendUserAction(SessionDetailUserActionEnum.UNSTAR, null)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mAddScheduleButton?.announceForAccessibility(if (starred)
                    getString(R.string.session_details_a11y_session_added)
                else
                    getString(R.string.session_details_a11y_session_removed))
            }
        }
    }
    override fun onScrollChanged(deltaX: Int, deltaY: Int) {






    }
    private val  mGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        mAddScheduleButtonContainerHeightPixels = mAddScheduleButtonContainer?.getHeight() as Int;
        recomputePhotoAndScrollingMetrics();
    }

    private fun recomputePhotoAndScrollingMetrics() {
        mHeaderHeightPixels = mHeaderBox!!.height

        mPhotoHeightPixels = 0
        if (mHasPhoto) {
            mPhotoHeightPixels = (mPhotoView!!.width / SessionDetailActivityFragment.PHOTO_ASPECT_RATIO).toInt()
            mPhotoHeightPixels = Math.min(mPhotoHeightPixels, mScrollView!!.height * 2 / 3)
        }

        val lp: ViewGroup.LayoutParams
        lp = mPhotoViewContainer!!.layoutParams
        if (lp.height != mPhotoHeightPixels) {
            lp.height = mPhotoHeightPixels
            mPhotoViewContainer!!.layoutParams = lp
        }

        val mlp = mDetailsContainer!!.layoutParams as ViewGroup.MarginLayoutParams
        if (mlp.topMargin != mHeaderHeightPixels + mPhotoHeightPixels) {
            mlp.topMargin = mHeaderHeightPixels + mPhotoHeightPixels
            mDetailsContainer!!.layoutParams = mlp
        }

        onScrollChanged(0, 0) // trigger scroll handling
    }
    override fun onResume() {
        super.onResume()
            displayData()
        mTimeHintUpdaterRunnable?.let {
            mHandler?.postDelayed(it,
                    SessionDetailConstants.TIME_HINT_UPDATE_INTERVAL)
        }
    }

    private fun displayData() {
        val schedule = activity.intent.extras.getSerializable(SessionDetailActivity.EXTRA_SCHEDULE) as ScheduleItem
        displaySessionData(schedule.session, schedule)
        displaySpeakerData(schedule.session.speakers, schedule)

    }

    private fun displaySpeakerData(speakers: List<Speaker>, data: ScheduleItem) {
        val speakersGroup =  activity.findViewById(R.id.session_speakers_block) as ViewGroup
            for(i in speakersGroup.childCount..0){
                speakersGroup.removeViewAt(i)
            }
        val inflater = LayoutInflater.from(activity)
        var hasSpeakers = false
        speakers.forEach {
            var speakerHeader = it.name
            if(!TextUtils.isEmpty(it.company)){
                speakerHeader += ", " +it.company;
            }
           val speakerView = inflater
                    .inflate(R.layout.speaker_detail, speakersGroup, false)
            val speakerHeaderView =  speakerView
                    .findViewById(R.id.speaker_header) as TextView
            val speakerImageView =  speakerView
                    .findViewById(R.id.speaker_image) as ImageView
            val speakerAbstractView =  speakerView
                    .findViewById(R.id.speaker_abstract) as TextView
            val plusOneIcon =  speakerView.findViewById(R.id.gplus_icon_box) as ImageView
            val twitterIcon =  speakerView.findViewById(R.id.twitter_icon_box) as ImageView

            setUpSpeakerSocialIcon(it, twitterIcon, it.twitterUrl ,
                    UIUtills.TWITTER_COMMON_NAME, UIUtills.TWITTER_PACKAGE_NAME);

            setUpSpeakerSocialIcon(it, plusOneIcon, it.plusOneUrl,
                    UIUtills.GOOGLE_PLUS_COMMON_NAME, UIUtills.GOOGLE_PLUS_PACKAGE_NAME)

            // A speaker may have both a Twitter and GPlus page, only a Twitter page or only a
            // GPlus page, or neither. By default, align the Twitter icon to the right and the GPlus
            // icon to its left. If only a single icon is displayed, align it to the right.
            determineSocialIconPlacement(plusOneIcon, twitterIcon)

            if (!TextUtils.isEmpty(it.photoUrl) && mSpeakersImageLoader != null) {
                mSpeakersImageLoader?.loadImage(it.photoUrl, speakerImageView);
            }

            speakerHeaderView.setText(speakerHeader)
            speakerImageView.setContentDescription(
                    getString(R.string.speaker_googleplus_profile, speakerHeader));
            UIUtills.setTextMaybeHtml(speakerAbstractView, it.title)
            speakersGroup.addView(speakerView);
            hasSpeakers = true
        }
        speakersGroup.setVisibility(if(hasSpeakers)  View.VISIBLE else View.GONE)
        updateEmptyView(data)
    }

    private fun updateEmptyView(data: ScheduleItem) {
        activity.findViewById(android.R.id.empty).setVisibility(
                if(data.title != null && data.session.speakers.size() == 0
                        && !TextUtils.isEmpty(data.session.description))
                 View.VISIBLE
                else View.GONE)

    }

    /**
     * Aligns the Twitter icon the parent bottom right. Aligns the G+ icon to the left of the
     * Twitter icon if it is present. Otherwise, aligns the G+ icon to the parent bottom right.
     */
    private fun determineSocialIconPlacement(plusOneIcon: ImageView, twitterIcon: ImageView) {
        if (plusOneIcon.getVisibility() == View.VISIBLE) {
            // Set the dimensions of the G+ button.
           val socialIconDimension = getResources().getDimensionPixelSize(
                    R.dimen.social_icon_box_size);
            val params =  RelativeLayout.LayoutParams(
                    socialIconDimension, socialIconDimension);
            params.addRule(RelativeLayout.BELOW, R.id.speaker_abstract);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            if (twitterIcon.getVisibility() == View.VISIBLE) {
                params.addRule(RelativeLayout.LEFT_OF, R.id.twitter_icon_box);
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            plusOneIcon.setLayoutParams(params);
        }
    }

    private fun setUpSpeakerSocialIcon(speaker: Speaker, socialIcon: ImageView, socialUrl: String?, socialNetworkName: String, packageName: String) {
        if (socialUrl == null || socialUrl.isEmpty()) {
            socialIcon.visibility = View.GONE
        } else {
            socialIcon.contentDescription = getString(
                    R.string.speaker_social_page,
                    socialNetworkName,
                    speaker.name)

            socialIcon.setOnClickListener{
                    UIUtills.fireSocialIntent(
                            getActivity(),
                            Uri.parse(socialUrl),
                            packageName)


            }
        }
    }



    private fun displaySessionData(data: Session, scheduleItem:ScheduleItem) {
        data?.let {
            mTitle?.text = it.title
            mSubtitle?.text=  scheduleItem.startTime.concat(" - ").concat(scheduleItem.endTime)

            mPhotoViewContainer
                    ?.setBackgroundColor(UIUtills.scaleSessionColorToDefaultBG(R.color.fab_icon_color))
            mHasPhoto = false
            recomputePhotoAndScrollingMetrics()

            displayTags(it)

            mAbstract?.text = it.description


            mLiveStreamVideocamIconAndText?.visibility = View.GONE

            mHandler?.post {
                    onScrollChanged(0, 0); // trigger scroll handling
                    mScrollViewChild?.setVisibility(View.VISIBLE);
                mAbstract?.setTextIsSelectable(true);

            }

            mTimeHintUpdaterRunnable = Runnable() {

                    if (getActivity() == null) {
                        // Do not post a delayed message if the activity is detached.

                    }
                    updateTimeBasedUi(scheduleItem);
                    mHandler?.postDelayed(mTimeHintUpdaterRunnable,
                            SessionDetailConstants.TIME_HINT_UPDATE_INTERVAL);

            }
            mHandler?.postDelayed(mTimeHintUpdaterRunnable,
                    SessionDetailConstants.TIME_HINT_UPDATE_INTERVAL);
        }

    }

    private fun updateTimeBasedUi(data: ScheduleItem) {
        data?.live(LIVE_DAY,{
            mLiveStreamVideocamIconAndText?.visibility = View.VISIBLE
        },{
            mLiveStreamVideocamIconAndText?.visibility = View.GONE
        })

        val timeHintView =  activity.findViewById(R.id.time_hint) as TextView
        timeHintView.text = data.startTime.concat(" - ").concat(data.endTime)

    }

    private fun displayTags(data: Session) {
        mTags?.removeAllViews()
        data.tags?.forEach {
            mTagsContainer?.setVisibility(View.VISIBLE)

            val inflater = LayoutInflater.from(context)
            val chipView =  inflater.inflate(
                    R.layout.include_session_tag_chip, mTags, false) as TextView
            chipView.text = it
            chipView.contentDescription = getString(R.string.talkback_button, it)
            mTags?.addView(chipView)
        }
    }

    override fun onPause() {
        super.onPause()
        mHandler?.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mScrollView == null) {
            return
        }

     mScrollView?.viewTreeObserver?.let {
            if (it.isAlive) {
                it.removeGlobalOnLayoutListener(mGlobalLayoutListener)
            }
        }

    }
}
