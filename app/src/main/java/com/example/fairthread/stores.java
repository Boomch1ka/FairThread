package com.example.fairthread;

public class stores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stores)

        findViewById<Button>(R.id.btnNike).setOnClickListener {
            startActivity(Intent(this, CatalogueActivity::class.java))
        }
    }
}
