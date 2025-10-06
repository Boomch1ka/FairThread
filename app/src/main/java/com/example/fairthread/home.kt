package com.example.fairthread

class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val searchBar = findViewById<EditText>(R.id.searchBar)
        searchBar.setOnEditorActionListener { _, _, _ ->
            Toast.makeText(this, "Searching: ${searchBar.text}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}