package io.github.t45k.isucon14_lt

import java.time.LocalDate

/**
 * https://github.com/t45k/Slide.kt
 */
fun main() {
    val presentation = presentation(PresentationOption(darkMode = true)) {
        cover(title = "TRIGGER難しい2024", name = "Task") {
            date(LocalDate.of(2024, 12, 27))
        }

        slide {
            title("失敗を語りに来ました")
            image(Path("failure.png"))
        }

        slide {
            textBox {
                s("総距離計算")
            }
        }

        slide {
            code(
                """SELECT id, owner_id, name, access_token, model,
       is_active, created_at, updated_at,
       IFNULL(total_distance, 0) AS total_distance,
       total_distance_updated_at
FROM chairs
LEFT JOIN (SELECT chair_id,
             SUM(IFNULL(distance, 0)) AS total_distance,
             MAX(created_at)          AS ...
  FROM (SELECT chair_id, created_at,
          ABS(latitude - LAG(latitude) OVER ...) +
          ABS(longitude - LAG(longitude) OVER ...)
        FROM chair_locations) tmp
  GROUP BY chair_id) distance_table ON ...
WHERE owner_id = ?"""
            )
        }

        slide {
            textBox(horizontalPosition = Horizontal.LEFT) {
                s("総距離計算は") {
                    n("前回の位置")
                    n("前回までの距離")
                }
                s("があれば計算できるので\nキャッシュすると速い")
            }
        }

        slide {
            title("どうキャッシュする")
            textBox(horizontalPosition = Horizontal.LEFT) {
                i("アプリでインメモリに")
                i("テーブルを追加して、アプリで計算して入れる")
            }
        }

        slide {
            textBox(horizontalPosition = Horizontal.LEFT) {
                s("どちらも/initializeにアプリ側で")
                s("計算する処理が必要なので面倒\n")
                s("レコードの追加時にMySQLで")
                s("計算して自動更新したい")
            }
        }

        slide {
            textBox {
                s(bold(color("TRIGGER", Color.RED)))
            }
        }

        slide {
            image(Path("trigger.png"))
        }

        slide {
            textBox {
                s("特定のテーブルに更新が走った時に")
                s("別のテーブルに何かをする")
            }
        }

        slide {
            textBox {
                s("chair_locationsテーブルに")
                s("INSERTが走った時に")
                s("total_distancesテーブルの")
                s("レコードを更新する")
            }
        }

        slide {
            image(Path("q.png"))
        }

        slide {
            image(Path("a.png"))
        }

        slide {
            title("結果")
            image(Path("res.png"))
        }

        slide {
            textBox {
                s(color("総距離計算の高速化", Color.GREEN))
                s(color("500のペナルティ", Color.RED))
                s("結果スコアは変わらず")
            }
        }

        slide {
            title("原因")
            textBox { s("何かがデッドロックしてる") }
        }

        slide {
            title("有識者へ")
            textBox { s("何か知ってたら教えてください 🙇") }
        }

        slide {
            textBox { s("最後に宣伝") }
        }

        slide {
            textBox {
                s("今日のスライドは自作OSSで作りました")
                s("https://github.com/t45k/Slide.kt")
                s("Starください")
            }
        }
    }

    handlePresentation(presentation)
}
