package com.vasanth.kmm.apparchitecture.android.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.vasanth.kmm.apparchitecture.android.R
import com.vasanth.kmm.apparchitecture.redux.Decrement
import com.vasanth.kmm.apparchitecture.redux.Increment
import com.vasanth.kmm.apparchitecture.redux.reducer
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.threadsafe.createThreadSafeStore


class LoginFragment : Fragment() {
    val store = createThreadSafeStore(reducer, 0)
    private lateinit var storeSubscription: StoreSubscription

    private lateinit var txtLabel: TextView

    // region Fragment Methods
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.frag_login, container, false)
        txtLabel = root.findViewById<TextView>(R.id.txt_label)
        storeSubscription = store.subscribe { render(store.state) }
        root.findViewById<AppCompatImageButton>(R.id.btn_increment)
            .setOnClickListener { store.dispatch(Increment()) }
        root.findViewById<AppCompatImageButton>(R.id.btn_decrement).setOnClickListener {
            store.dispatch(
                Decrement()
            )
        }
        root.findViewById<Button>(R.id.btn_async).setOnClickListener { incrementAsync() }
        root.findViewById<Button>(R.id.btn_increment_if_odd)
            .setOnClickListener { incrementIfOdd() }
        return root
    }

    private fun render(state: Int) {
        txtLabel.text = "Clicked: $state times"
    }

    private fun incrementIfOdd() {
        if (store.state % 2 != 0) {
            store.dispatch(Increment())
        }
    }

    private fun incrementAsync() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                store.dispatch(Increment())
            },
            1000
        )
    }
}