
package com.example.mysupertuffapp

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var content: LinearLayout
    private val cart = mutableMapOf<Int, Int>()

    private val names = arrayOf(
        "RX 9070 XT", "RX 9070", "RX 9060 XT",
        "RX 7900 XTX", "RX 7900 XT", "RX 7900 GRE",
        "RX 7800 XT", "RX 7700 XT", "RX 7600 XT",
        "RX 7600", "RX 6750 XT", "RX 6650 XT"
    )

    private val specs = arrayOf(
        "16GB GDDR6 • RDNA 4",
        "16GB GDDR6 • RDNA 4",
        "16GB GDDR6 • RDNA 4",
        "24GB GDDR6 • RDNA 3",
        "20GB GDDR6 • RDNA 3",
        "16GB GDDR6 • RDNA 3",
        "16GB GDDR6 • RDNA 3",
        "12GB GDDR6 • RDNA 3",
        "16GB GDDR6 • RDNA 3",
        "8GB GDDR6 • RDNA 3",
        "12GB GDDR6 • RDNA 2",
        "8GB GDDR6 • RDNA 2"
    )

    private val prices = intArrayOf(
        749, 649, 349, 999, 799, 599,
        499, 399, 329, 269, 299, 229
    )

    private val red = Color.rgb(255, 45, 55)
    private val white = Color.WHITE
    private val grey = Color.rgb(160, 160, 175)
    private val panel = Color.rgb(25, 25, 33)
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        content = findViewById(R.id.content)
        showHome()
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun rounded(color: Int, radius: Int): GradientDrawable {
        val shape = GradientDrawable()
        shape.setColor(color)
        shape.cornerRadius = dp(radius).toFloat()
        return shape
    }

    private fun label(
        parent: LinearLayout,
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        val view = TextView(this)
        view.text = value
        view.textSize = size
        view.setTextColor(color)
        if (bold) {
            view.setTypeface(null, Typeface.BOLD)
        }
        view.setPadding(0, dp(5), 0, dp(5))
        parent.addView(view)
        return view
    }

    private fun button(
        parent: LinearLayout,
        value: String,
        action: () -> Unit
    ) {
        val view = Button(this)
        view.text = value
        view.textSize = 14f
        view.isAllCaps = false
        view.setTextColor(white)
        view.background = rounded(red, 12)

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(50)
        )
        params.setMargins(0, dp(7), 0, dp(7))
        parent.addView(view, params)
        view.setOnClickListener { action() }
    }

    private fun card(): LinearLayout {
        val view = LinearLayout(this)
        view.orientation = LinearLayout.VERTICAL
        view.setPadding(dp(16), dp(15), dp(16), dp(15))
        view.background = rounded(panel, 17)

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, dp(8), 0, dp(8))
        content.addView(view, params)
        return view
    }

    private fun clear() {
        content.removeAllViews()
    }

    private fun showHome() {
        clear()

        val header = card()
        label(header, "AMD  /  RADEON", 13f, red, true)
        label(header, "POWER YOUR", 27f, white, true)
        label(header, "GAMING.", 35f, red, true)
        label(
            header,
            "Discover graphics cards built for performance.",
            14f,
            grey
        )

        val search = EditText(this)
        search.hint = "Search graphics cards..."
        search.setTextColor(white)
        search.setHintTextColor(grey)
        search.setSingleLine(true)
        search.setPadding(dp(15), 0, dp(15), 0)
        search.background = rounded(panel, 12)

        val searchParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(54)
        )
        searchParams.setMargins(0, dp(12), 0, dp(12))
        content.addView(search, searchParams)

        label(content, "FEATURED GRAPHICS CARDS", 20f, white, true)
        label(content, "12 Radeon GPUs available", 13f, grey)

        val productsContainer = LinearLayout(this)
        productsContainer.orientation = LinearLayout.VERTICAL
        content.addView(productsContainer)

        fun showProducts(query: String) {
            productsContainer.removeAllViews()

            var found = false

            for (i in names.indices) {
                if (!names[i].contains(query, true)) {
                    continue
                }

                found = true

                val product = LinearLayout(this)
                product.orientation = LinearLayout.VERTICAL
                product.setPadding(dp(16), dp(14), dp(16), dp(14))
                product.background = rounded(panel, 17)

                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, dp(7), 0, dp(7))
                productsContainer.addView(product, params)

                val visual = TextView(this)
                visual.text = "RADEON"
                visual.gravity = Gravity.CENTER
                visual.textSize = 25f
                visual.setTypeface(null, Typeface.BOLD)
                visual.setTextColor(red)
                visual.background = rounded(
                    Color.rgb(43, 17, 22),
                    13
                )
                product.addView(
                    visual,
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(100)
                    )
                )

                label(product, "AMD GRAPHICS  •  IN STOCK", 11f, red, true)
                label(product, names[i], 21f, white, true)
                label(product, specs[i], 13f, grey)
                label(product, "€${prices[i]}", 25f, red, true)

                button(product, "ADD TO CART") {
                    cart[i] = (cart[i] ?: 0) + 1
                    Toast.makeText(
                        this,
                        "${names[i]} added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (!found) {
                label(productsContainer, "No GPUs found.", 16f, grey)
            }
        }

        showProducts("")

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                showProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        button(content, "SHOPPING CART (${cart.values.sum()})") {
            showCart()
        }

        button(content, "MY LAST ORDER") {
            showLastOrder()
        }

        label(content, "RADEON STORE • DEMO PROJECT", 11f, grey)
    }

    private fun total(): Int {
        var sum = 0
        for ((id, quantity) in cart) {
            sum += prices[id] * quantity
        }
        return sum
    }

    private fun showCart() {
        clear()

        label(content, "YOUR CART", 28f, white, true)
        label(content, "Review your selected GPUs.", 14f, grey)

        if (cart.isEmpty()) {
            val empty = card()
            label(empty, "YOUR CART IS EMPTY", 19f, white, true)
            label(empty, "Add a graphics card to get started.", 14f, grey)
        }

        for ((id, quantity) in cart.toMap()) {
            val item = card()
            label(item, names[id], 20f, white, true)
            label(item, specs[id], 13f, grey)
            label(item, "Quantity: $quantity", 15f, white)
            label(item, "€${prices[id] * quantity}", 23f, red, true)

            button(item, "REMOVE ONE") {
                if (quantity <= 1) {
                    cart.remove(id)
                } else {
                    cart[id] = quantity - 1
                }
                showCart()
            }
        }

        val summary = card()
        label(summary, "TOTAL", 13f, grey, true)
        label(summary, "€${total()}", 29f, red, true)

        if (cart.isNotEmpty()) {
            button(content, "PROCEED TO CHECKOUT") {
                showCheckout()
            }
        }

        button(content, "CONTINUE SHOPPING") {
            showHome()
        }
    }

    private fun input(hint: String): EditText {
        val edit = EditText(this)
        edit.hint = hint
        edit.setTextColor(white)
        edit.setHintTextColor(grey)
        edit.setSingleLine(true)
        edit.setPadding(dp(13), 0, dp(13), 0)
        edit.background = rounded(panel, 11)

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(53)
        )
        params.setMargins(0, dp(5), 0, dp(5))
        content.addView(edit, params)
        return edit
    }

    private fun showCheckout() {
        clear()

        label(content, "CHECKOUT", 28f, white, true)
        label(content, "DELIVERY DETAILS", 15f, red, true)

        val name = input("Full name")
        val email = input("Email address")
        val street = input("Street")
        val house = input("House number")
        val postcode = input("Postcode")
        val city = input("City")

        val summary = card()
        label(summary, "YOUR TOTAL", 13f, grey, true)
        label(summary, "€${total()}", 28f, red, true)

        label(
            content,
            "Demo checkout. No real payment or shipping.",
            13f,
            grey
        )

        button(content, "PLACE DEMO ORDER") {
            val fields = listOf(
                name, email, street, house, postcode, city
            )

            if (fields.any {
                    it.text.toString().trim().isEmpty()
                }) {
                Toast.makeText(
                    this,
                    "Please fill in every field",
                    Toast.LENGTH_SHORT
                ).show()
                return@button
            }

            val customerEmail = email.text.toString().trim()

            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(customerEmail).matches()) {
                email.error = "Enter a valid email address"
                return@button
            }

            val orderId = "AMD-" +
                    System.currentTimeMillis().toString().takeLast(6)

            val customer = name.text.toString()
            val address = "${street.text} ${house.text}, " +
                    "${postcode.text} ${city.text}"
            val amount = total()

            getSharedPreferences("amd_store", MODE_PRIVATE)
                .edit()
                .putString("order_id", orderId)
                .putString("order_name", customer)
                .putString("order_address", address)
                .putInt("order_total", amount)
                .apply()

            cart.clear()
            showConfirmation(orderId, customer, address, amount)
        }

        button(content, "BACK TO CART") {
            showCart()
        }
    }

    private fun showConfirmation(
        id: String,
        customer: String,
        address: String,
        amount: Int
    ) {
        clear()

        val panelView = card()
        label(panelView, "ORDER CONFIRMED", 26f, red, true)
        label(panelView, "Thank you, $customer!", 17f, white)
        label(panelView, "ORDER NUMBER", 12f, grey, true)
        label(panelView, id, 22f, white, true)

        label(panelView, "DELIVERY ADDRESS", 13f, grey, true)
        label(panelView, address, 16f, white)

        label(panelView, "TOTAL", 13f, grey, true)
        label(panelView, "€$amount", 28f, red, true)

        label(
            panelView,
            "PROJECT DEMO: No money was charged and no products will be shipped.",
            13f,
            grey
        )

        button(content, "BACK TO STORE") {
            showHome()
        }
    }

    private fun showLastOrder() {
        val prefs = getSharedPreferences("amd_store", MODE_PRIVATE)
        val id = prefs.getString("order_id", null)

        if (id == null) {
            clear()
            label(content, "NO ORDERS YET", 25f, red, true)
            button(content, "BACK TO STORE") {
                showHome()
            }
            return
        }

        showConfirmation(
            id,
            prefs.getString("order_name", "") ?: "",
            prefs.getString("order_address", "") ?: "",
            prefs.getInt("order_total", 0)
        )
    }
}
