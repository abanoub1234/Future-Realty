package com.example.futurerealty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class FavoritScreen : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var realtyAdapter: RealtyAdapter
    private val favoriteList = ArrayList<realityData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorit_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.FavoritRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        realtyAdapter = RealtyAdapter(favoriteList, object : RealtyAdapter.OnItemClickListener {
            override fun onItemClick(realtyData: realityData) {
                // Handle item click if needed
            }
        })
        recyclerView.adapter = realtyAdapter

        loadFavorites()
    }

    private fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private fun loadFavorites() {
        val userId = getCurrentUserId()
        lifecycleScope.launch(Dispatchers.IO) {
            userId?.let { uid ->
                val favoriteRealtyData = FavoriteRealtyDatabase.getDatabase(requireContext()).favoriteRealtyDao().getAllFavorites(uid)
                favoriteList.clear()
                favoriteList.addAll(favoriteRealtyData.map {
                    realityData(it.imageUrl, it.price, it.description, it.location, it.contact)
                })
                withContext(Dispatchers.Main) {
                    realtyAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
