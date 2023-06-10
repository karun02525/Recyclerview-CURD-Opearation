package com.exae.recyclerviewcurd

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.exae.recyclerviewcurd.data.MovieModel

internal class MoviesAdapter(private var moviesList: ArrayList<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(moviesList[position], moviesList, position)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var year: TextView = view.findViewById(R.id.year)
        var genre: TextView = view.findViewById(R.id.genre)
        var cl: CardView = view.findViewById(R.id.cl)
        fun bind(movie: MovieModel, moviesList: ArrayList<MovieModel>, position: Int) {
            movie.apply {
                title.text = getTitle()
                year.text = getYear()
                genre.text = getGenre()
            }

            cl.setOnClickListener {
                val dialog = Dialog(it.context)
                dialog.setContentView(R.layout.custome_dialog)
                val titleDialog = dialog.findViewById<EditText>(R.id.title)
                val genreDialog = dialog.findViewById<EditText>(R.id.genre)
                val yearDialog = dialog.findViewById<EditText>(R.id.year)
                val btnUpdate = dialog.findViewById<Button>(R.id.btnUpdate)
                titleDialog.setText(movie.getTitle())
                yearDialog.setText(movie.getYear())
                genreDialog.setText(movie.getGenre())
                btnUpdate.setOnClickListener {
                    moviesList.set(
                        position,
                        MovieModel(
                            titleDialog.text.toString(),
                            genreDialog.text.toString(),
                            yearDialog.text.toString()
                        )
                    )
                    notifyItemChanged(position)
                    dialog.dismiss()
                }
                dialog.show()
            }
            cl.setOnLongClickListener {
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(it.context)
                alertDialog.setTitle("AlertDialog")
                alertDialog.setMessage("Do you want to delete?")
                alertDialog.setPositiveButton(
                    "yes"
                ) { _, _ ->
                    moviesList.removeAt(position)
                    notifyItemRemoved(position)

                }
                alertDialog.setNegativeButton(
                    "No"
                ) { _, _ -> }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                true
            }

        }


    }
}