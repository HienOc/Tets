package com.adnet.testgit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adnet.testgit.dragDrop.DragDropItemTouchHelperCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var list = mutableListOf<String>().apply {
        add("1")
        add("2")
        add("3")
        add("4")
    }
    private val namesAdapter: NamesAdapter by lazy {
        NamesAdapter(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcList.run {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = namesAdapter
        }

        val dragDropCallback = DragDropItemTouchHelperCallback(
            namesAdapter,
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        )
        val itemTouchHelper = ItemTouchHelper(dragDropCallback)
        namesAdapter.dataList = list
        namesAdapter.itemTouchHelper = itemTouchHelper
        itemTouchHelper.attachToRecyclerView(rcList)
    }
}
