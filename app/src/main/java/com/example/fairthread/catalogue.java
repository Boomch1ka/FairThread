package com.example.fairthread;

class catalogue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        findViewById<Button>(R.id.btnStock).setOnClickListener {
            startActivity(Intent(this, DisplayActivity::class.java))
        }
    }
}
