package ru.geekbrains.android2.movieapp.contacts

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle(getString(R.string.contacts_alert_title))
                            .setMessage(getString(R.string.contacts_alert_message))
                            .setNegativeButton(getString(R.string.contacts_alert_button)) { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    private fun getContacts() {
        context?.let {
            val cursorWithContacts: Cursor? = it.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor'е
                    if (cursor.moveToPosition(i)) {
                        // Берём из Cursor'а столбец с именем
                        val name =
                            cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                            )
                        val id = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
                        )
                        val hasPhone = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                        )
                        if (hasPhone.toInt() > 0) {
                            val cursorPhones: Cursor? = it.contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + "=$id",
                                null,
                                null
                            )
                            val phonesList = mutableListOf<String>()
                            cursorPhones?.let { cursorPh ->
                                for (j in 0..cursorPh.count) {
                                    if (cursorPh.moveToPosition(j)) {
                                        phonesList.add(
                                            cursorPh.getString(
                                                cursorPh.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                            )
                                        )
                                    }
                                }
                            }
                            cursorPhones?.close()
                            if (phonesList.size > 0) {
                                addView(it, name, phonesList[0])
                            }
                        }
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun addView(context: Context, textToShow: String, phoneNumber: String) = with(binding) {
        containerForContacts.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.contacts_text_size)
            tag = phoneNumber
            setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", it.tag as String, null))
                startActivity(intent)
            }
        })
    }

    companion object {
        private const val REQUEST_CODE = 42

        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}