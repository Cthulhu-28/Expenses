package co.cr.expenses.data

import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.data.base.DataManager
import co.cr.expenses.data.base.Response
import co.cr.expenses.model.Detail
import co.cr.expenses.model.ExpendingDay
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.utils.timestamp
import com.google.firebase.database.*
import java.util.*

class FirebaseDataManager: DataManager{
    val database = FirebaseDatabase.getInstance()
    val listeners = hashMapOf<DatabaseReference, ValueEventListener>()

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

  /*  override fun getDaySummary(date: Date, event: DataEvent<Summary>) {
        val reference = database.reference.child("days/${date.timestamp()}/summary")
        val listener = object: ValueEventListener{
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
        }
        reference.addValueEventListener(listener)
        listeners.put(reference, listener)
    }*/

    override fun getDaySummary(date: Date, event: DataEvent<Summary>, attach: Boolean) {
        val reference = database.reference.child("days/${date.timestamp()}/summary")
        val listener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val summary = p0.getValue(Summary::class.java)
                    event.onSuccess(Response(summary!!))
                } else {
                    event.onSuccess(Response(Summary()))
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError("Canceled")
            }
        }
        if(attach) {
            reference.addValueEventListener(listener)
            listeners.put(reference, listener)
        }else{
            reference.addListenerForSingleValueEvent(listener)
        }

    }

    override fun getSummary(event: DataEvent<Summary>) {
        val reference = database.reference.child("days/summary")
        val listener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val summary = p0.getValue(Summary::class.java)
                    event.onSuccess(Response(summary!!))
                } else {
                    event.onSuccess(Response(Summary()))
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError("Canceled")
            }
        }
        reference.addListenerForSingleValueEvent(listener)
    }

    override fun getExpenditures(date: Date, event: DataEvent<List<Expenditure>>) {
        val query = database.reference.child("days/${date.timestamp()}/expenditures")
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val expenditures = mutableListOf<Expenditure>()
                if(datasnapshot.exists()){
                    for(snapshot in datasnapshot.children){
                        val expenditure = snapshot.getValue(Expenditure::class.java)!!
                        expenditure.time = snapshot.key!!.toInt()
                        expenditures.add(expenditure)
                    }
                }
                event.onSuccess(Response(expenditures))
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError("Canceled")
            }
        })
    }

    override fun getIncome(date: Date, event: DataEvent<List<Detail>>) {
        val query = database.reference.child("days/${date.timestamp()}/income")
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val incomes = mutableListOf<Detail>()
                if(datasnapshot.exists()){
                    for(snapshot in datasnapshot.children){
                        val income = snapshot.getValue(Detail::class.java)!!
                        income.time = snapshot.key!!.toInt()
                        incomes.add(income)
                    }
                }
                event.onSuccess(Response(incomes))
            }

            override fun onCancelled(p0: DatabaseError) {
                event.onError("Canceled")
            }
        })
    }

    override fun deleteExpenditure(date: Date, expenditure: Expenditure, event: DataEvent<Expenditure>) {
        val reference = database.reference.child("days/${date.timestamp()}/expenditures/${expenditure.time}")
        reference.removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    event.onSuccess(Response(expenditure))
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

    override fun deleteIncome(date: Date, detail: Detail, event: DataEvent<Detail>) {
        val reference = database.reference.child("days/${date.timestamp()}/income/${detail.time}")
        reference.removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    event.onSuccess(Response(detail))
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

    override fun destroy() {
        listeners.forEach {
            listeners.remove(it.key)
        }
    }
}