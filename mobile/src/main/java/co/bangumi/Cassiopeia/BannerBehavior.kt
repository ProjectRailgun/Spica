package co.bangumi.Cassiopeia

import android.animation.Animator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class BannerBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout?,
        child: View?,
        dependency: View?
    ): Boolean {
        return   dependency is FrameLayout;
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout?,
        child: View,
        dependency: View?
    ): Boolean {
        if (dependency != null && dependency is FrameLayout) {
            val swipeRefreshLayout = dependency.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
            val recyclerView = swipeRefreshLayout.getChildAt(0) as RecyclerView
            val list: RecyclerView.OnScrollListener
            recyclerView.setOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (child.visibility == View.VISIBLE) hide(child)
                    } else{
                        if (child.visibility == View.GONE) show(child)
                    }
                }
            })

//            if (isBottom(swipeRefreshLayout.getChildAt(0) as RecyclerView))
//                hide(child!!) else show(child!!)
        }
        return true
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        child.canScrollVertically(1)
    }

    private fun isTop(view: RecyclerView): Boolean {
        return !view.canScrollVertically(1)
    }

    private fun isBottom(view: RecyclerView): Boolean {
        val layoutManager = view.getLayoutManager() as LinearLayoutManager;
        //屏幕中最后一个可见子项的position
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        val visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        val totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        val state = view.getScrollState()
        val lastView = view.getChildAt(visibleItemCount - 1)
        return  lastView is TextView && lastView.text.equals(view.context.getString(R.string.more))
    }

    private fun hide(view: View) {
        val animator =
            view.animate().translationY(view.height.toFloat()).setInterpolator(INTERPOLATOR)
                .setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animator: Animator) {
                show(view)
            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
        animator.start()
    }

    private fun show(view: View) {
        val animator =
            view.animate().translationY(view.height.toFloat()).setInterpolator(INTERPOLATOR)
                .setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animator: Animator) {
                hide(view)
            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
        animator.start()

    }

    companion object {

        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }
}