package com.example.fairthread;

class order : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        findViewById<Button>(R.id.btnPayOrders).setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }
}
