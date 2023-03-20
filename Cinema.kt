package cinema

fun main() {



    // Initial configuration
    println("Enter the number of rows: ")
    val numberOfRows = readln().toInt()
    println("Enter the number of seats in each row: ")
    val seatsPerRow = readln().toInt()
    val seatingArrangement = Array(numberOfRows) { Array(seatsPerRow) { "S" } }
    val numberOfSeats = numberOfRows * seatsPerRow

    var numberOfPurchasedTickets = 0
    var currentIncome = 0
    var percentageOfOccupancy = "%.2f".format(numberOfPurchasedTickets.toDouble() / numberOfSeats.toDouble())
    var choice: Int
    var totalIncome = 0

    if (numberOfSeats < 60) {
        totalIncome = numberOfSeats*10
    } else {
        if (numberOfRows % 2 == 0) {
           totalIncome = (10 + 8) * (numberOfRows / 2) * seatsPerRow
        } else {
           totalIncome = 10 * ((numberOfRows-1) / 2) * seatsPerRow + 8 * ((numberOfRows+1) / 2) * seatsPerRow
        }
    }

    // Takes an array representing a Cinema and prints it
    fun cinema(seatingArrangement : Array<Array<String>>) {

        println("Cinema:")
        print("  ")
        for (seatNumber in 1..seatsPerRow) print("$seatNumber ")
        println()
        for (rowNumber in 1..numberOfRows) {
            print("$rowNumber ")
            for (seatNumber in 1..seatsPerRow) {
                print("${seatingArrangement[rowNumber - 1][seatNumber - 1]} ")
            }
            println()
        }
    }

    // Prints a menu and returns the choice made by the user
    fun menu() : Int {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        return readln().toInt()
    }

    // Prints the price menu and updates the state of the cinema
    fun priceMenu() {

        println("Enter a row number: ")
        val row = readln().toInt()
        println("Enter a seat number in that row: ")
        val seat = readln().toInt()

        if ((seat in 1..seatsPerRow) && (row in 1..numberOfRows)){

            //Mark the chosen seat with a B

                if (seatingArrangement[row - 1][seat - 1] != "B") {

                    seatingArrangement[row - 1][seat - 1] = "B"

                    numberOfPurchasedTickets += 1
                    percentageOfOccupancy = "%.2f".format(numberOfPurchasedTickets.toDouble() / numberOfSeats.toDouble() *100)

                    if (numberOfSeats < 60) {
                        println("Ticket price: $10")
                        currentIncome += 10
                    } else {

                        var priceTicket = 0
                        var rowsFirstHalf = 0
                        var rowsBackHalf = 0

                        if (numberOfRows % 2 == 0) {
                            rowsFirstHalf = numberOfRows / 2
                            rowsBackHalf = numberOfRows / 2

                            if (row <= rowsFirstHalf) {
                                priceTicket = 10
                                currentIncome += 10
                            } else {
                                priceTicket = 8
                                currentIncome += 8
                            }
                        } else {
                            rowsFirstHalf = (numberOfRows - 1) / 2
                            rowsBackHalf = (numberOfRows - 1) / 2 + 1

                            if (row <= rowsFirstHalf) {
                                priceTicket = 10
                                currentIncome += 10
                            } else {
                                priceTicket = 8
                                currentIncome += 8
                            }
                        }

                        println("Ticket price: $" + priceTicket.toString())
                    }
                }
                else{
                    println("That ticket has already been purchased!")
                    priceMenu()
                }

        }
        else{
            println("Wrong input!")
            priceMenu()
        }


    }

    // Prints the statistics for a given cinema state
    fun statistics(totalIncome:Int,numberOfPurchasedTickets:Int,currentIncome:Int,percentage:String){
        println("Number of purchased tickets: " + numberOfPurchasedTickets.toString())
        println("Percentage: " + percentage + "%")
        println("Current income: " + "$" + currentIncome.toString())
        println("Total income: " + "$" + totalIncome.toString())
    }


    while (true) {

        when (menu()) {

            1 -> {
                cinema(seatingArrangement)
            }

            2 -> {
                priceMenu()
            }

            3 -> {
                statistics(totalIncome, numberOfPurchasedTickets, currentIncome, percentageOfOccupancy)
            }

            0 -> return

        }
    }

}