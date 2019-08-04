package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    private lateinit var viewModel: ProfileViewModel
    lateinit var viewFields: Map<String, TextView>
    var isRepositoryValid = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUi(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateUi(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }
        }
    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false

        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

        et_repository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateRepository(s.toString())
            }
        })

        if (isEditMode) {
            validateRepository(et_repository.text.toString())
        }

        showCurrentMode(isEditMode);
    }

    private fun validateRepository(repository: String) {
        isRepositoryValid = checkRepositoryValidity(repository)
        if (!isRepositoryValid && isEditMode) {
            wr_repository.error = "Невалидный адрес репозитория"
            wr_repository.isErrorEnabled = true
        } else {
            wr_repository.error = null
            wr_repository.isErrorEnabled = false
        }
    }

    private fun checkRepositoryValidity(repository: String): Boolean {
        if (repository == "") return true

        val regExp: Regex = "^(https://)?(www\\.)?github\\.com/([\\da-zA-Z]+-?[\\da-zA-Z]+)/?$".toRegex()
        val matches = regExp.find(repository)

        val restrictedUrls = listOf("enterprise", "features", "topics", "collections", "trending", "events", "marketplace", "pricing", "nonprofit", "customer-stories", "security", "login", "join")
        if (matches != null) {
            val rest = matches.groupValues[3]
            return !restrictedUrls.contains(rest)
        } else {
            return false
        }
    }

    private fun showCurrentMode(isEditMode: Boolean) {
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }

        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEditMode
            v.isFocusableInTouchMode = isEditMode
            v.isEnabled = isEditMode
            v.background.alpha = if (isEditMode) 255 else 0
        }

        if (!isEditMode) {
            wr_repository.error = null
            wr_repository.isErrorEnabled = false
        }

        ic_eye.visibility = if (isEditMode) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEditMode

        with(btn_edit) {
            val filter: ColorFilter? = if (isEditMode) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon = if (isEditMode) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo() {
        if (!isRepositoryValid) {
            et_repository.setText("")
        }

        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }
}

