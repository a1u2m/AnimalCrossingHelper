package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossinghelper.databinding.FragmentListBinding
import com.example.animalcrossinghelper.room.*
import com.example.animalcrossinghelper.utils.gone
import com.example.animalcrossinghelper.utils.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class ListFragment : Fragment() {

    val TAG = "ListFragment"

    lateinit var binding: FragmentListBinding
    private val model: MainViewModel by inject()
    private val itemList = mutableListOf<Any>()
    private val fishDao: FishDao by inject()
    private val bugDao: BugDao by inject()
    private val fossilhDao: FossilDao by inject()
    private val seaCreatureDao: SeaCreatureDao by inject()
    private val prefs: SharedPreferencesHelper by inject()

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        val handler = ListFragmentHandler()
        binding.handler = handler
        val verticalLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycler.layoutManager = verticalLayout
        binding.recycler.adapter = ListFragmentFishAdapter(requireContext(), itemList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db: List<Any>
        when (model.category) {
            MainViewModel.Category.Fish -> {
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                            db = fishDao.getUserBase(prefs.getIdOfLoggedUser())
                            fillItemList(db)
                        }
                    }
                }
            }
            MainViewModel.Category.Bug -> {
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                            db = bugDao.getUserBase(prefs.getIdOfLoggedUser())
                            fillItemList(db)
                        }
                    }
                }
            }
            MainViewModel.Category.SeaCreature -> {
                binding.locationTv.gone()
                binding.locationSpinner.gone()
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                            db = seaCreatureDao.getUserBase(prefs.getIdOfLoggedUser())
                            fillItemList(db)
                        }
                    }
                }
            }
            MainViewModel.Category.Fossil -> {
                binding.locationTv.gone()
                binding.locationSpinner.gone()
                binding.monthSpinner.gone()
                binding.monthTv.gone()
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                            db = fossilhDao.getUserBase(prefs.getIdOfLoggedUser())
                            fillItemList(db)
                        }
                    }
                }
            }
        }

        model.updateRecycler.observe(viewLifecycleOwner) {
            binding.recycler.adapter?.notifyItemRemoved(it)
            binding.recycler.adapter?.notifyItemRangeChanged(it, binding.recycler.adapter!!.itemCount)
        }
    }

    private fun fillItemList(items: List<Any>) {
        when (model.category) {
            MainViewModel.Category.Fish -> {
                for (i in items.indices) {
                    itemList.add(items[i] as Fish)
                }
            }
            MainViewModel.Category.Bug -> {
                for (i in items.indices) {
                    itemList.add(items[i] as Bug)
                }
            }
            MainViewModel.Category.SeaCreature -> {
                for (i in items.indices) {
                    itemList.add(items[i] as SeaCreature)
                }
            }
            MainViewModel.Category.Fossil -> {
                for (i in items.indices) {
                    itemList.add(items[i] as Fossil)
                }
            }
        }

    }

    inner class ListFragmentHandler {
        fun goBack(view: View) {
            navigate(R.id.hubFragment)
        }
    }
}