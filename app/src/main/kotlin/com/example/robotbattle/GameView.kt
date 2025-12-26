package com.example.robotbattle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var gameThread: GameThread? = null
    private val paint = Paint()
    private var playerX = 100f
    private var playerY = 100f
    private var enemyX = 700f
    private var enemyY = 500f
    private var playerHealth = 100
    private var enemyHealth = 100

    init {
        holder.addCallback(this)
        paint.color = Color.RED
        paint.textSize = 50f
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread = GameThread(holder)
        gameThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameThread?.interrupt()
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {}
    }

    fun startBattle(player: Robot, enemy: Robot) {
        playerHealth = player.totalHealth()
        // Similar for enemy
        enemyHealth = enemy.totalHealth()
        // Start simulation
    }

    inner class GameThread(private val surfaceHolder: SurfaceHolder) : Thread() {
        override fun run() {
            while (!interrupted()) {
                val canvas: Canvas? = surfaceHolder.lockCanvas()
                canvas?.let {
                    it.drawColor(Color.BLACK)

                    // Draw arena
                    paint.color = Color.GRAY
                    it.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

                    // Draw player robot
                    paint.color = Color.BLUE
                    it.drawCircle(playerX, playerY, 30f, paint)

                    // Draw enemy
                    paint.color = Color.RED
                    it.drawCircle(enemyX, enemyY, 30f, paint)

                    // Simple movement
                    playerX += Random.nextFloat() - 0.5f * 2 // random walk
                    enemyX -= Random.nextFloat() - 0.5f * 2
                    // Boundary check
                    playerX = playerX.coerceIn(30f, (width - 30).toFloat())
                    enemyX = enemyX.coerceIn(30f, (width - 30).toFloat())

                    // Draw health
                    paint.color = Color.WHITE
                    it.drawText("Player HP: $playerHealth", 10f, 50f, paint)
                    it.drawText("Enemy HP: $enemyHealth", 10f, 100f, paint)

                    // Simple battle: if close, damage
                    val dist = Math.sqrt(((playerX - enemyX) * (playerX - enemyX) + (playerY - enemyY) * (playerY - enemyY)).toDouble()).toFloat()
                    if (dist < 100) {
                        playerHealth -= 5
                        enemyHealth -= 5
                        if (playerHealth <= 0 || enemyHealth <= 0) {
                            // End battle
                            interrupt()
                        }
                    }

                    surfaceHolder.unlockCanvasAndPost(it)
                }
                try {
                    sleep(100)
                } catch (e: InterruptedException) {
                    interrupt()
                }
            }
        }
    }
}
