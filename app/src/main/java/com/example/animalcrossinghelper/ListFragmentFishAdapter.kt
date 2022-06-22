package com.example.animalcrossinghelper

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossinghelper.room.*
import com.example.animalcrossinghelper.utils.gone
import com.example.animalcrossinghelper.utils.load
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import java.util.concurrent.TimeUnit

class ListFragmentFishAdapter(private val context: Context, private val list: MutableList<Any>) :
        RecyclerView.Adapter<ListFragmentFishAdapter.ViewHolder>() { //todo может если вместо Fish подставить Any то норм будет, протестить когда с рыбой на 100% разберусь

    private val TAG = "ViewPagerAdapter"

    private val timeCountObservable = Observable.interval(1, TimeUnit.SECONDS).take(5)
    lateinit var timeCountDisposable: DisposableObserver<Long>
    private val fishDao: FishDao by inject()
    private val prefs: SharedPreferencesHelper by inject()
    private val model: MainViewModel by inject()
    private var fishList = mutableListOf<Fish>()
    private var fossilList = mutableListOf<Fossil>()
    private var bugList = mutableListOf<Bug>()
    private var seaCreaturesList = mutableListOf<SeaCreature>()

    private val inflater = LayoutInflater.from(context)

    init {
        KTP.openRootScope().inject(this)
        when (model.category) {
            MainViewModel.Category.Fish -> {
                fishList = list as MutableList<Fish>
            }
            MainViewModel.Category.Bug -> {
                bugList = list as MutableList<Bug>
            }
            MainViewModel.Category.Fossil -> {
                fossilList = list as MutableList<Fossil>
            }
            MainViewModel.Category.SeaCreature -> {
                seaCreaturesList = list as MutableList<SeaCreature>
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.creature_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (model.category) {
            MainViewModel.Category.Fish -> {
                val itemsList = fishList[position]
                val pictureLink = itemsList.iconUri
                with(holder) {
                    val monthList = itemsList.monthArray
                    for (i in monthList) {
                        if (isAllYear(monthList)) months.text = "Круглый год"
                        else months.text = prepareMonths(monthList)
                    }
                    val timeList = itemsList.timeArray
                    for (i in timeList) {
                        if (isAllDay(timeList)) time.text = "В любое время"
                        else time.text = prepareTime(timeList)
                    }
                    name.text =
                            "Название: ${
                                itemsList.name.replaceFirstChar {
                                    it.uppercase()
                                }
                            }" //todo текст в стрингу и от ворнинга избавиться
                    rarity.text =
                            "Попадается: ${convertRarity(itemsList.rarity)}" //todo текст в стрингу и от ворнинга избавиться
                    location.text =
                            "Локация: ${convertLocation(itemsList.location)}" //todo текст в стрингу и от ворнинга избавиться
                    price.text = "Цена: ${itemsList.price}" //todo текст в стрингу и от ворнинга избавиться
                    reminder.setOnClickListener {
                        //todo тут чето с воркменежером подмутить
                    }
                    check.setOnClickListener {
                        createNewDisposableAndSubscribe(itemsList.id, position)
                        Snackbar.make(
                                itemView,
                                R.string.entry_deleted,
                                5000
                        ).setAction(
                                "Отмена"
                        ) { timeCountDisposable.dispose() }.show()
                    }
                }
                load(pictureLink, holder.icon)
            }
            MainViewModel.Category.Bug -> {
                val itemsList = bugList[position]
                val pictureLink = itemsList.iconUri
                with(holder) {
                    val monthList = itemsList.monthArray
                    for (i in monthList) {
                        if (isAllYear(monthList)) months.text = "Круглый год"
                        else months.text = prepareMonths(monthList)
                    }
                    val timeList = itemsList.timeArray
                    for (i in timeList) {
                        if (isAllDay(timeList)) time.text = "В любое время"
                        else time.text = prepareTime(timeList)
                    }
                    name.text =
                            "Название: ${
                                itemsList.name.replaceFirstChar {
                                    it.uppercase()
                                }
                            }" //todo текст в стрингу и от ворнинга избавиться
                    rarity.text =
                            "Попадается: ${convertRarity(itemsList.rarity)}" //todo текст в стрингу и от ворнинга избавиться
                    location.text =
                            "Локация: ${convertLocation(itemsList.location)}" //todo текст в стрингу и от ворнинга избавиться
                    price.text = "Цена: ${itemsList.price}" //todo текст в стрингу и от ворнинга избавиться
                    reminder.setOnClickListener {
                        //todo тут чето с воркменежером подмутить
                    }
                    check.setOnClickListener {
                        createNewDisposableAndSubscribe(itemsList.id, position)
                        Snackbar.make(
                                itemView,
                                R.string.entry_deleted,
                                5000
                        ).setAction(
                                "Отмена"
                        ) { timeCountDisposable.dispose() }.show()
                    }
                }
                load(pictureLink, holder.icon)
            }
            MainViewModel.Category.SeaCreature -> {
                val itemsList = seaCreaturesList[position]
                val pictureLink = itemsList.iconUri
                with(holder) {
                    val monthList = itemsList.monthArray
                    for (i in monthList) {
                        if (isAllYear(monthList)) months.text = "Круглый год"
                        else months.text = prepareMonths(monthList)
                    }
                    val timeList = itemsList.timeArray
                    for (i in timeList) {
                        if (isAllDay(timeList)) time.text = "В любое время"
                        else time.text = prepareTime(timeList)
                    }
                    name.text =
                            "Название: ${
                                itemsList.name.replaceFirstChar {
                                    it.uppercase()
                                }
                            }" //todo текст в стрингу и от ворнинга избавиться
                    price.text = "Цена: ${itemsList.price}" //todo текст в стрингу и от ворнинга избавиться
                    reminder.setOnClickListener {
                        //todo тут чето с воркменежером подмутить
                    }
                    check.setOnClickListener {
                        createNewDisposableAndSubscribe(itemsList.id, position)
                        Snackbar.make(
                                itemView,
                                R.string.entry_deleted,
                                5000
                        ).setAction(
                                "Отмена"
                        ) { timeCountDisposable.dispose() }.show()
                    }
                    rarity.gone()
                    location.gone()
                }
                load(pictureLink, holder.icon)
            }
            MainViewModel.Category.Fossil -> {
                val itemsList = fossilList[position]
                with(holder) {
                    name.text =
                            "Название: ${
                                itemsList.name.replaceFirstChar {
                                    it.uppercase()
                                }
                            }" //todo текст в стрингу и от ворнинга избавиться
                    price.text = "Цена: ${itemsList.price}" //todo текст в стрингу и от ворнинга избавиться
                    reminder.setOnClickListener {
                        //todo тут чето с воркменежером подмутить
                    }
                    check.setOnClickListener {
                        createNewDisposableAndSubscribe(itemsList.id, position)
                        Snackbar.make(
                                itemView,
                                R.string.entry_deleted,
                                5000
                        ).setAction(
                                "Отмена"
                        ) { timeCountDisposable.dispose() }.show()
                    }
                    icon.gone()
                    months.gone()
                    time.gone()
                    rarity.gone()
                    location.gone()
                    reminder.gone()
                }
            }
        }

    }

    private fun createNewDisposableAndSubscribe(itemId: Long, position: Int) {
        timeCountDisposable = object : DisposableObserver<Long>() {

            override fun onNext(t: Long?) {
                //do nothing
            }

            override fun onError(t: Throwable?) {
                Log.d(TAG, "timeCount onError: ${t?.message}")
            }

            override fun onComplete() {
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                            fishDao.deleteFromUserById(prefs.getIdOfLoggedUser(), itemId)
                        }
                    }
                }
                runBlocking {
                    launch(Dispatchers.Main) {
                        list.removeAt(position)
                        model.updateRecycler.value = position
                    }
                }
                Log.d(TAG, "ЗАПИСЬ УДАЛЕНА")
            }

        }
        timeCountObservable.subscribe(timeCountDisposable)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun prepareMonths(list: List<String>): String {
        val sb = StringBuilder()
        for ((count, i) in list.withIndex()) {
            if (count % 3 == 0) {
                sb.append("\n")
            }
            val month = convertMonthToHuman(i)
            sb.append("$month, ")
        }
        return sb.toString().dropLast(2)
    }

    private fun prepareTime(list: List<String>): String {
        val sb = StringBuilder()
        var isNewCycle = true
        var penult = list[0].toInt()
        var count = 1
        for (i in list) {
            if (isNewCycle) {
                sb.append(penult)
                penult = i.toInt()
                count++
                isNewCycle = false
            } else if (penult + 1 == i.toInt() && count == list.size) {
                sb.append(" - ")
                sb.append(i)
                penult = i.toInt()
                count++
            } else if (i.toInt() == 0 && count != list.size) {
                penult = i.toInt()
                count++
            } else if (penult + 1 == i.toInt()) {
                penult = i.toInt()
                count++
            } else {
                sb.append(" - ")
                sb.append(penult)
                sb.append(", ")
                count++
                penult = i.toInt()
                isNewCycle = true
            }
        }
        return sb.toString()
    }

    private fun convertMonthToHuman(month: String): String {
        val humanMonth: String = when (month) {
            "1" -> "Январь"
            "2" -> "Февраль"
            "3" -> "Март"
            "4" -> "Апрель"
            "5" -> "Май"
            "6" -> "Июнь"
            "7" -> "Июль"
            "8" -> "Август"
            "9" -> "Сентябрь"
            "10" -> "Октябрь"
            "11" -> "Ноябрь"
            "12" -> "Декабрь"
            else -> "Ошибка"
        }
        return humanMonth
    }

    private fun isAllDay(timeList: List<String>): Boolean {
        return timeList.size == 24
    }

    private fun isAllYear(monthList: List<String>): Boolean {
        return monthList.size == 12
    }

    private fun convertRarity(rarity: String): String {
        val russianRarity: String = when (rarity) {
            "Common" -> "Часто"
            "Uncommon" -> "Нечасто"
            "Rare" -> "Редко"
            "Ultra-rare" -> "Очень редко"
            else -> "Ошибка"
        }
        return russianRarity
    }

    private fun convertLocation(location: String): String {
        val russianLocation: String = when (location) {
            "Sea (when raining or snowing)" -> "Море в дождь или снегопад"
            "Sea" -> "Море"
            "River (Mouth)" -> "Устье реки"
            "River (Clifftop) & Pond" -> "Горная река или пруд"
            "River (Clifftop)" -> "Горная река"
            "River" -> "Река"
            "Pond" -> "Пруд"
            "Pier" -> "Пирс"
            else -> "Ошибка"
        }
        return russianLocation
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var check: AppCompatButton = itemView.findViewById(R.id.item_got_it)
        var name: AppCompatTextView = itemView.findViewById(R.id.item_name)
        var icon: AppCompatImageView = itemView.findViewById(R.id.item_icon)
        var months: AppCompatTextView =
                itemView.findViewById(R.id.item_months)
        var time: AppCompatTextView = itemView.findViewById(R.id.item_time)
        var rarity: AppCompatTextView = itemView.findViewById(R.id.item_rarity)
        var location: AppCompatTextView = itemView.findViewById(R.id.item_location)
        var price: AppCompatTextView = itemView.findViewById(R.id.item_price)
        var reminder: AppCompatButton =
                itemView.findViewById(R.id.item_reminder)
    }
}