package com.example.fairthread;

class cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItems = listOf("Item 1", "Item 2", "Item 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cartItems)
        findViewById<ListView>(R.id.cartList).adapter = adapter

        findViewById<Button>(R.id.btnPayCart).setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }
}
