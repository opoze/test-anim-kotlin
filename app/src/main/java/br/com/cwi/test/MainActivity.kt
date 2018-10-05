package br.com.cwi.test

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private var anims: Spinner? = null
    private var ball: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anims = findViewById<Spinner>(R.id.anim_spinner)
        ball = findViewById<ImageView>(R.id.ball);
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.anim_options, android.R.layout.simple_spinner_item)
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Apply the adapter to the spinner
        anims!!.setAdapter(adapter)
        anims!!.setOnItemSelectedListener(this);
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2 > 0) {
            if (p2 == 1) {
                translate()
            } else if (p2 == 2) {
                rotate()
            } else if (p2 == 3) {
                fade()
            } else if (p2 == 4) {
                scale()
            } else if (p2 == 5) {
                combin_anims()
            }else if (p2 == 6) {
                valueAnim()
            }
        }

    }

    private fun translate() {
        val ty1 = ObjectAnimator.ofFloat(ball, View.TRANSLATION_Y, 0f, 200f)
        ty1.setDuration(1000)
        ty1.interpolator = BounceInterpolator()
        ty1.start()
    }

    private fun rotate() {
        val rotate = ObjectAnimator.ofFloat(ball, View.ROTATION, -360f, 0f)
        rotate.setDuration(1000)
        rotate.interpolator = AccelerateInterpolator()
        rotate.start()
    }

    private fun fade() {
        val fade = ObjectAnimator.ofFloat(ball, View.ALPHA, 0.2f, 1.0f)
        fade.setDuration(1000)
        fade.start()
    }

    private fun scale() {
        val anims = AnimatorSet();
        val sX = ObjectAnimator.ofFloat(ball, View.SCALE_X, 0.2f, 1.0f)
        val sY = ObjectAnimator.ofFloat(ball, View.SCALE_Y, 0.2f, 1.0f)
        anims.playTogether(sX, sY)
        anims.interpolator = AccelerateInterpolator()
        anims.start()
    }

    private fun combin_anims() {

        val anims1 = AnimatorSet()
        val sX = ObjectAnimator.ofFloat(ball, View.SCALE_X, 0.2f, 1.0f)
        val sY = ObjectAnimator.ofFloat(ball, View.SCALE_Y, 0.2f, 1.0f)
        val fade = ObjectAnimator.ofFloat(ball, View.ALPHA, 0.2f, 1.0f)

        anims1.playTogether(sX, sY, fade)
        anims1.setDuration(600)

        val anims2 = AnimatorSet()

        val tx1 = ObjectAnimator.ofFloat(ball, View.TRANSLATION_Y, 0f, 200f)
        tx1.setDuration(1000)
        tx1.interpolator = BounceInterpolator()

        val rotate = ObjectAnimator.ofFloat(ball, View.ROTATION, -360f, 0f)
        rotate.setDuration(1000)
        rotate.interpolator = AccelerateInterpolator()

        anims2.playTogether(tx1, rotate)

        val final_anim = AnimatorSet();

        final_anim.play(anims1).before(anims2)
        final_anim.play(anims2)

        final_anim.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                Toast.makeText(applicationContext,"Animation Fininsed",Toast.LENGTH_SHORT).show();
            }

            override fun onAnimationStart(p0: Animator?) {
                Toast.makeText(applicationContext,"Animation Started",Toast.LENGTH_SHORT).show();
            }

        });

        final_anim.start()

    }

    private fun valueAnim(){
        val tx = ValueAnimator.ofFloat(200f, 0f)
        val mDuration = 1000 //in millis
        tx.duration = mDuration.toLong()
        tx.addUpdateListener { animation -> ball!!.setTranslationX(animation.animatedValue as Float) }
        tx.start()
    }

}