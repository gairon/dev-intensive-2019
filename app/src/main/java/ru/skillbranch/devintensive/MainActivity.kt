package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    val BUNDLE_STATUS = "STATUS";
    val BUNDLE_QUESTION = "QUESTION";
    val BUNDLE_MESSAGE = "MESSAGE";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString(BUNDLE_STATUS) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(BUNDLE_QUESTION) ?: Bender.Question.NAME.name
        val message = savedInstanceState?.getString(BUNDLE_MESSAGE) ?: ""

        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY);

        textTxt.text = benderObj.askQuestion()

        messageEt.setText(message);
        messageEt.setOnEditorActionListener(this);

        sendBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            handleSendAction()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(BUNDLE_STATUS, benderObj.status.name)
        outState?.putString(BUNDLE_QUESTION, benderObj.question.name)
        outState?.putString(BUNDLE_MESSAGE, messageEt.text.toString())
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (view?.id == R.id.et_message && EditorInfo.IME_ACTION_DONE == actionId) {
            handleSendAction()
            hideKeyboard();
            return true;
        }

        return false;
    }

    private fun handleSendAction() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY);
        textTxt.text = phrase
    }
}
