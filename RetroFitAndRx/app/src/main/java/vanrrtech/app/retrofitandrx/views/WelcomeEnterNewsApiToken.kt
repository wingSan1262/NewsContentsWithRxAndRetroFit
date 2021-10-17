package vanrrtech.app.retrofitandrx.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.utils.Utils


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeEnterNewsApiToken.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeEnterNewsApiToken : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var mView : View? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_welcome_enter_news_api_token, container, false)
        mView?.findViewById<TextView>(R.id.open_news_api)?.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org/"))
            startActivity(browserIntent)
        }
        mView?.findViewById<TextView>(R.id.use_default_api_key)?.setOnClickListener {
            Utils.getUtils().saveKey(requireContext(), "f2aefd4d5a474ac386ff27389ff67d43")
            (activity as WelcomeActivity).startApp("f2aefd4d5a474ac386ff27389ff67d43")
        }
        mView?.findViewById<EditText>(R.id.token_field)?.addTextChangedListener(object : TextWatcher,
            View.OnFocusChangeListener {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var bool = false
                if(s?.length!! > 25){
                    bool = true
                }
                (activity as WelcomeActivity).findViewById<Button>(R.id.start_button).isEnabled = bool
            }

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
            }

        })
        return mView
    }

    fun startFromTextField(){
        val token  = mView?.findViewById<EditText>(R.id.token_field)?.text.toString()
        Utils.getUtils().saveKey(requireContext(), token)
        (activity as WelcomeActivity).startApp(token)
        (activity as WelcomeActivity).finish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WelcomeEnterNewsApiToken.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeEnterNewsApiToken().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}