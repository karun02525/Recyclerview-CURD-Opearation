package com.exae.recyclerviewcurd

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exae.recyclerviewcurd.data.MovieModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val movieList:ArrayList<MovieModel> = PrepareMovieData.getData()
    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

       val btnAction: FloatingActionButton = findViewById(R.id.btnAction)
        btnAction.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custome_dialog)
            val title = dialog.findViewById<EditText>(R.id.title)
            val genre = dialog.findViewById<EditText>(R.id.genre)
            val year = dialog.findViewById<EditText>(R.id.year)
            val btnUpdate = dialog.findViewById<Button>(R.id.btnUpdate)
            btnUpdate.setOnClickListener {
                 movieList.add(MovieModel(title.text.toString(),genre.text.toString(),year.text.toString()))
                 moviesAdapter.notifyItemInserted(movieList.size-1)
                 recyclerView.scrollToPosition(movieList.size-1)
                dialog.dismiss()
            }

            dialog.show()
        }



        moviesAdapter = MoviesAdapter(movieList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = moviesAdapter
    }
}