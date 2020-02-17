package co.cr.expenses.data

import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.data.base.DataManager
import co.cr.expenses.data.base.Response
import co.cr.expenses.model.Detail
import co.cr.expenses.model.ExpendingDay
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.utils.timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseDataManager: DataManager{
    val database = FirebaseDatabase.getInstance()

    override fun findExpendingDay(date: Date, event: DataEvent<ExpendingDay>) {
        val reference = database.reference.child("days")
        val day = ExpendingDay()
        reference.orderByKey().equalTo(date.timestamp().toString()).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                day.time = date.timestamp()
                if(dataSnapshot.exists()){
                    event.onSuccess(Response(day))
                }else{
                    reference.child(day.time.toString()).setValue(day)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                event.onSuccess(Response(day))
                            }else{
                                event.onError(it.exception?.message!!)
                            }
                        }
                        .addOnFailureListener {
                            event.onError(it.message!!)
                        }
                        .addOnCanceledListener {
                            event.onError("Error")
                        }

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError(p0.message)
            }
        })
    }

    override fun addIncome(day: ExpendingDay, income: Detail, event: DataEvent<ExpendingDay>) {
        val reference = database.reference
            .child("days/${day.time}/income")
            .child("${income.time}")
        reference.setValue(income)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    day.income[income.time] = income
                    event.onSuccess(Response(day))
                }else{
                    event.onError(it.exception?.message!!)
                }
            }
            .addOnFailureListener {
                event.onError(it.message!!)
            }
            .addOnCanceledListener {
                event.onError("Canceled")
            }
    }

    override fun addExpenditure(day: ExpendingDay, expenditure: Expenditure, event: DataEvent<ExpendingDay>) {
        val reference = database.reference
            .child("days/${day.time}/expenditures")
            .child("${expenditure.time}")
        reference.setValue(expenditure)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    day.income[expenditure.time] = expenditure
                    event.onSuccess(Response(day))
                }else{
                    event.onError(it.exception?.message!!)
                }
            }
            .addOnFailureListener {
                event.onError(it.message!!)
            }
            .addOnCanceledListener {
                event.onError("Canceled")
            }
    }

    override fun getDaySummary(date: Date, event: DataEvent<Summary>) {
        val query = database.reference.child("days/${date.timestamp()}/summary")
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val summary = p0.getValue(Summary::class.java)
                    event.onSuccess(Response(summary!!))
                }else{
                    event.onSuccess(Response(Summary()))
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError("Canceled")
            }
        });
    }
}