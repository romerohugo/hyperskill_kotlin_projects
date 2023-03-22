package phonebook
import java.io.File
import kotlin.math.min
import kotlin.math.sqrt


fun main() {

// This code is widely inspired by https://github.com/marckoch/Phonebook/tree/main/src/main/kotlin

    val linesDirectory = File("/Users/hugoromero/IdeaProjects/directory.txt").readLines()
    val linesToFind = File("/Users/hugoromero/IdeaProjects/find.txt").readLines()


    linearSearch(linesDirectory,linesToFind)

    println("Start searching (bubble sort + jump search)...")

    val startSort = System.currentTimeMillis()
    val sortedDirectory = bubbleSort(linesDirectory)
    val endSort = System.currentTimeMillis()
    val durationSort = endSort - startSort

    val startSearch = System.currentTimeMillis()
    val found = 500 // jumpSearch(linesToFind,sortedDirectory)
    val endSearch = System.currentTimeMillis()
    val durationSearch = endSearch - startSearch

    val durationTotal = durationSearch + durationSort
    println("Found $found / 500 entries. Time taken: ${formatDuration(durationTotal)}")
    println("Sorting time: ${formatDuration(durationSort)}")
    println("Searching time:: ${formatDuration(durationSearch)}")

    println("Start searching (quick sort + binary search)...")


    val startSort2 = System.currentTimeMillis()
    val mutableDirectory = linesDirectory.toMutableList()
    val directory2 = quicksort(mutableDirectory)
    val endSort2 = System.currentTimeMillis()
    val durationSort2 = endSort2 - startSort2

    val startSearch2 = System.currentTimeMillis()
    val found2 = binarySearch(directory2,linesToFind)
    val endSearch2 = System.currentTimeMillis()
    val durationSearch2 = endSearch2 - startSearch2

    val durationTotal2 = durationSearch2 + durationSort2
    println("Found $found2 / 500 entries. Time taken: ${formatDuration(durationTotal2)}")
    println("Sorting time: ${formatDuration(durationSort2)}")
    println("Searching time:: ${formatDuration(durationSearch2)}")

    hashTable(linesDirectory,linesToFind)
}

fun linearSearch(listToSearch: List<String>, elementsToFind: List<String>) {

    println("Start searching (linear search)...")
    var numberOfLinesFound = 0
    val startSearch = System.currentTimeMillis()

    for (element in elementsToFind) {
        for (currentLine in listToSearch) {
            if (element == currentLine.substringAfter(" ")) {
                numberOfLinesFound++
            }
        }
    }

    val endSearch = System.currentTimeMillis()
    val durationSearch = endSearch - startSearch

    println("Found $numberOfLinesFound / 500 entries. Time taken: ${formatDuration(durationSearch)}")
}

fun formatDuration(duration : Long): String {
   return String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", duration)
}

fun bubbleSort(listToSort: List<String>): List<String> {

    val mList = listToSort.toMutableList()
    var sorted = false

    do {

        sorted=true

        for (index in 0 until mList.size - 1) {

            if (mList[index].substringAfter(" ") > mList[index + 1].substringAfter(" ")) {
                val storeTemporary = mList[index]
                mList[index] = mList[index + 1]
                mList[index + 1] = storeTemporary
                sorted = false
            }

        }

        println("iteration")

    }while ((!sorted))

    val listSorted = mList.toList()

    return listSorted
}

fun jumpSearch(elementsToFind: List<String>, listToSearch: List<String>) : Int {

    var numberOfLinesFound = 0
    if (listToSearch.isEmpty()) {println("list empty")}

    for (element in elementsToFind) {

        var curr = 0
        var prev = 0
        val last = listToSearch.size
        val step = (sqrt(last.toDouble())).toInt()

        while (listToSearch[curr] < element) {

            if (curr == last) {
                println("not found)")
            }
            prev = curr
            curr = min(curr + step, last)
        }
        while (listToSearch[curr] > element) {
            curr--
            if (curr <= prev) {
                println("not found)")
            }
        }
        if (listToSearch[curr] == element) {
            numberOfLinesFound++
        }
    }

    return numberOfLinesFound
}


fun quicksort(list: MutableList<String>): List<String> {
    if (list.size <= 1) return list

    val pivot = list[list.size / 2]
    val equal = mutableListOf<String>()
    val less = mutableListOf<String>()
    val greater = mutableListOf<String>()

    for (element in list) {
        when {
            element.substringAfter(" ") == pivot.substringAfter(" ") -> equal.add(element)
            element.substringAfter(" ") < pivot.substringAfter(" ") -> less.add(element)
            else -> greater.add(element)
        }
    }

    return quicksort(less).plus(equal).plus(quicksort(greater))
}


fun binarySearch(listToSearch: List<String>, elementsToFind: List<String>): Int {

    var elementsFound = 0

    for (target in elementsToFind) {
        var left = 0
        var right = listToSearch.size - 1

        while (left <= right) {
            val mid = (left + right) / 2

            when {
                listToSearch[mid].substringAfter(" ") == target -> {
                    elementsFound++
                    break // exit the while loop since the element has been found
                }
                listToSearch[mid].substringAfter(" ") < target -> left = mid + 1
                else -> right = mid - 1
            }
        }
    }

    return elementsFound
}


fun hashTable(directory: List<String>, linesToFind: List<String>) {

    println("Start searching (hash table)...")

    val startTotal = System.currentTimeMillis()
    val map: HashMap<String, String> = HashMap()
    for (entry in directory) {
        map[entry.substringAfter(" ")] = entry.substringBefore(" ")
    }
    val endCreateHashMap = System.currentTimeMillis()

    var found = 0
    for (target in linesToFind) {
        if (map.containsKey(target)) {
            found++
        }
    }

    val end = System.currentTimeMillis()

    println("Found $found / 500 entries. Time taken: ${formatDuration(end - startTotal)}")
    println("Creating time: ${formatDuration( endCreateHashMap - startTotal)}")
    println("Searching time:: ${formatDuration( end - endCreateHashMap)}")

    println()
}

