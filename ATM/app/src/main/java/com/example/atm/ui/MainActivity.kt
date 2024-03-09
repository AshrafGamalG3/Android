package com.example.atm.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atm.R
import com.example.atm.data.UserData
import com.example.atm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var balanceTextView: TextView

    private val depositAmountEditText by lazy { findViewById<EditText>(R.id.depositAmountEditText) }
    private val submitButton by lazy { findViewById<Button>(R.id.submitButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().reference
        onClick()
        balanceTextView = findViewById(R.id.balanceTextView)
    }

    private fun onClick() {
        binding.viewBalance.setOnClickListener {
            depositAmountEditText.visibility=View.GONE
            submitButton.visibility=View.GONE
            validVisa()

        }

        binding.withdrawFunds.setOnClickListener {
            val numVisa = binding.visa.text.toString()
            val numCvv = binding.cvv.text.toString()
            balanceTextView.visibility=View.GONE
            withdrawFunds(numVisa,numCvv)
        }

        binding.depositFunds.setOnClickListener {
            val numVisa = binding.visa.text.toString()
            val numCvv = binding.cvv.text.toString()
            balanceTextView.visibility=View.GONE
            depositFunds(numVisa, numCvv)

        }
    }

    private fun validVisa() {
        val numVisa = binding.visa.text.toString()
        val numCvv = binding.cvv.text.toString()
        if (numVisa.length == 16 && numCvv.length == 3) {
            validInRealTime(numVisa, numCvv)
        } else {
            showToast("Visa number must be 16 digits and CVV must be 3 digits")
        }
    }

    private fun validInRealTime(numVisa: String, numCvv: String) {
        ref.child("users").child(numCvv)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val visa = userData.cardNumber
                            if (visa == numVisa) {
                                balanceTextView.visibility = View.VISIBLE

                                balanceTextView.text = "Balance: ${userData.balance}$"
                            } else {
                                showToast("Visa number does not match.")
                            }
                        } else {
                            showToast("No user data found for the provided CVV.")
                        }
                    } else {
                        showToast("No data exists for the provided CVV.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Error: ${databaseError.message}")
                }
            })
    }

    private fun isValidVisa(numVisa: String, numCvv: String, callback: (Boolean) -> Unit) {
        ref.child("users").child(numCvv)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val visa = userData.cardNumber
                            callback(visa == numVisa)
                        } else {

                            callback(false)
                        }
                    } else {

                        callback(false)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Error: ${databaseError.message}")
                    callback(false)
                }
            })
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun withdrawFunds(numVisa: String, numCvv: String) {
        isValidVisa(numVisa, numCvv) { isValid ->
            if (isValid) {
                showToast("Visa card is valid. You can proceed with the deposit.")

                depositAmountEditText.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE

                submitButton.setOnClickListener {
                    val amount = depositAmountEditText.text.toString().toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        depositAmountEditText.visibility=View.GONE
                        submitButton.visibility=View.GONE
                        updateBalanceWithdrawFunds(amount, numCvv)

                    } else {
                        showToast("Please enter a valid amount.")
                    }
                }
            } else {
                showToast("Visa card is not valid. No user data found for the provided CVV.")
            }
        }
    }
    private fun depositFunds(numVisa: String, numCvv: String) {
        isValidVisa(numVisa, numCvv) { isValid ->
            if (isValid) {
                showToast("Visa card is valid. You can proceed with the deposit.")

                depositAmountEditText.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE

                submitButton.setOnClickListener {
                    val amount = depositAmountEditText.text.toString().toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        depositAmountEditText.visibility=View.GONE
                        submitButton.visibility=View.GONE
                        updateBalance(amount, numCvv)

                    } else {
                        showToast("Please enter a valid amount.")
                    }
                }
            } else {
                showToast("Visa card is not valid. No user data found for the provided CVV.")
            }
        }
    }


    private fun updateBalance(depositAmount: Double, numCvv: String) {
        ref.child("users").child(numCvv)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val currentBalance = userData.balance
                            val newBalance = currentBalance + depositAmount

                            ref.child("users").child(numCvv).child("balance").setValue(newBalance)
                                .addOnSuccessListener {
                                    showToast("Funds deposited successfully.")
                                }
                                .addOnFailureListener { e ->
                                    showToast("Failed to deposit funds: ${e.message}")
                                }
                        } else {
                            showToast("No user data found for the provided CVV.")
                        }
                    } else {
                        showToast("No data exists for the provided CVV.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Error: ${databaseError.message}")
                }
            })
    }private fun updateBalanceWithdrawFunds(depositAmount: Double, numCvv: String) {
        ref.child("users").child(numCvv)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(UserData::class.java)
                        if (userData != null) {
                            val currentBalance = userData.balance
                            val newBalance = currentBalance - depositAmount

                            ref.child("users").child(numCvv).child("balance").setValue(newBalance)
                                .addOnSuccessListener {
                                    showToast("Funds deposited successfully.")
                                }
                                .addOnFailureListener { e ->
                                    showToast("Failed to deposit funds: ${e.message}")
                                }
                        } else {
                            showToast("No user data found for the provided CVV.")
                        }
                    } else {
                        showToast("No data exists for the provided CVV.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Error: ${databaseError.message}")
                }
            })
    }
}
