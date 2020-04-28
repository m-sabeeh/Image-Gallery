package com.example.imagegallery.epoxy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.imagegallery.R

class PracticeFragment : Fragment() {
    private val recyclerView: EpoxyRecyclerView? by lazy { activity?.findViewById<EpoxyRecyclerView>(R.id.epoxy_rv) }

    private val singlePersonController: PersonController by lazy { PersonController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEpoxyRV()
    }

    fun initEpoxyRV() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView?.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = singlePersonController.adapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }
        singlePersonController.requestModelBuild()
    }

    class PersonController : EpoxyController() {
        var persons = PersonFactory.getPersonList(500)

        override fun buildModels() {
            var i: Long = 1
            persons.forEach { person ->
                SinglePersonModel_()
                        .id(++i)
                        .avatar(person.avatar)
                        .age(person.age)
                        .description(person.name)
                        .addTo(this)
            }
        }
    }
}