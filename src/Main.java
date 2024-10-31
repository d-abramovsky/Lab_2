import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int [][] matrix = new int[m][n];
        if(n>0&&m>0){
            // ЗАПИСЬ ЭЛЕМЕНТОВ
            int row = matrix.length;
            int col = matrix[0].length;
            for(int i = 0; i<row; i++){
                for(int j = 0; j<col; j++){
                    // Записываем элементы в матрицу
                    int number = scan.nextInt();
                    matrix[i][j] = number;
                }
            }
            System.out.println();

            // СОРТИРОВКА МАССИВА ПО УСЛОВИЮ
            // Создаем массив с аналитикой каждой строки
            // [сумма, медиана, дисперсия, изначальный номер строки в матрице] - структура каждого элемента
            double [][] analytics = new double[m][4];
            for(int i = 0; i<m; i++){
                int [] row_arr = new int[n];
                // Сохраняем исходную позицию
                analytics[i][3] = i;
                for (int j = 0; j<n; j++){
                    row_arr[j] = matrix[i][j];
                    // Считаем сумму строки
                    analytics[i][0] += matrix[i][j];
                }
                // Сортируем строку для поиска медианы
                int len = row_arr.length;
                while(len!=0) {
                    int index = 0;
                    for (int j = 1; j < len; j++) {
                        // Сравниваем элементы массива и если предыдущий больше нынешнего, меняем их местами
                        if (row_arr[j - 1] > row_arr[j]) {
                            int number = row_arr[j - 1];
                            row_arr[j - 1] = row_arr[j];
                            row_arr[j] = number;
                            index = j;
                        }
                    }
                    // Максимальный элемент гарантированно уйдет в конец, обновляем длину неотсортированной части
                    len = index;
                }
                // Ищем медиану
                if(n%2==0){
                    analytics[i][1] = (double) (row_arr[row_arr.length / 2 - 1] + row_arr[row_arr.length / 2]) / 2;
                }else{
                    analytics[i][1] = row_arr[row_arr.length/2];
                }
                // Находим среднее арифметическое
                double srd = analytics[i][0] / n;
                double dispers = 0;
                for(int j = 0; j <n; j++){
                    // Считаем сумму дисперсии каждого элемента
                    dispers +=  Math.pow(row_arr[j]-srd,2);
                }
                // Сохраняем дисперсию строки
                analytics[i][2] = dispers/n;
            }
            // Сортируем полученные данные по алгоритму из задания
            int len = analytics.length;
            while(len!=0){
                int index = 0;
                for(int i = 1;i<len;i++){
                    // Сортируем матрицу по первому элементу каждой строки
                    if(analytics[i-1][0] > analytics[i][0]) {
                        double[] number = analytics[i - 1];
                        analytics[i - 1] = analytics[i];
                        analytics[i] = number;
                        index = i;
                    }else if(analytics[i-1][0] == analytics[i][0]){
                        // Если они равны сортируем по второму элементу
                        if(analytics[i-1][1] > analytics[i][1]){
                            double[] number = analytics[i - 1];
                            analytics[i - 1] = analytics[i];
                            analytics[i] = number;
                            index = i;
                        }else if (matrix[i-1][1] == matrix[i][1]){
                            // Если вторые элементы равны, сортируем по третьему
                            if(analytics[i-1][2] > analytics[i][2]){
                                double[] number = analytics[i - 1];
                                analytics[i - 1] = analytics[i];
                                analytics[i] = number;
                                index = i;
                            }
                        }
                    }
                }
                // Так как после сортировки максимальный элемент уйдет в конец, перезаписываем длину неотсортированной части
                len = index;
            }
            int counter = 0;
            int [][] new_arr = new int[m][n];
            for(int i = 0; i<m; i++){
                for(int j = 0; j<n; j++){
                    new_arr[i][j] = matrix[i][j];
                }
            }
            // Обновляем изначальный массив
            for(int i = 0; i<m; i++){
                int numb = (int) analytics[counter][3];
                matrix[counter] = new_arr[numb];
                counter+=1;
            }
            //ВЫВОД МАТРИЦЫ
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // Выводим элементы матрицы
                    System.out.printf("%3d", matrix[i][j]);
                }
                System.out.println();
            }
            System.out.println();
            // ПОИСК МИНИМУМА
            // Возьмем за исходный элемент первый элемент первого столбца
            int max_min = 0;
            int column = 1;
            for(int i = 0; i<n; i++){
                // Ищем минимальный элемент столбца
                int min = matrix[0][i];
                for(int j = 0; j<m; j++){
                    if(matrix[j][i] < min){
                        min = matrix[j][i];
                    }
                }
                // Сравниваем найденный минимальный элемент с предыдущими
                // Если это первое сравнение, то записываем первый найденный минимум
                if(i!=0){
                    if(min>max_min){
                        // Обновляем его если он больше предыдущего
                        max_min = min;
                        column= i+1;
                    }
                }else{
                    max_min = min;

                }
            }
            // Выводим элемент и его координаты (Первый подходящий)
            System.out.println("Максимальный элемент среди минимальных: " + max_min);
            System.out.println("Элемент был найден в " + column + " столбце");
            System.out.println();
            //ВЫВОД СПИРАЛИ
            System.out.println("Вывод элементов матрицы по спирали начиная с левого верхнего элемента:\n");
            // Получаем кол-во всех элементов в матрице
            int elements = row * col;
            // Задаем направления
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 1. Право 2. Вниз 3. Влево 4. Вниз
            // Задаем исходное направление и координаты указателя
            int direct = 0;
            int curr_row = 0;
            int curr_col = 0;
            boolean[][] visited_position = new boolean[row][col];
            for (int i = 0; i < elements; i++) {
                // Выводим элемент
                System.out.printf("%3d",matrix[curr_row][curr_col]);
                // Помечаем элемент как пройденный
                visited_position[curr_row][curr_col] = true;
                // Считаем следующий элемент
                int next_row = curr_row + directions[direct][0];
                int next_col = curr_col + directions[direct][1];
                // Смотрим не вышли ли мы за границу матрицы
                if (next_row < 0 || next_row >= row || next_col < 0 || next_col >= col || visited_position[next_row][next_col]) {
                    //Если вышли, то меняем направление и считаем новые координаты
                    direct = (direct + 1) % 4;
                    next_row = curr_row + directions[direct][0];
                    next_col = curr_col + directions[direct][1];
                }
                // Сдвигаем указатель
                curr_row = next_row;
                curr_col = next_col;
            }
            System.out.println();
            System.out.println();
            System.out.println("Повернутая на 90 градусов матрица:\n");
            // Создаем новую матрицу
            int[][] new_matrix = new int[col][row];
            // Проходим по каждой строке исходной матрицы
            for (int i = 0; i < row; i++) {
                // Проходим по каждому столбцу исходной матрицы
                for (int j = 0; j < col; j++) {
                    // Заполняем новую матрицу, поворачивая исходную на 90 градусов по часовой стрелке
                    new_matrix[j][row - i - 1] = matrix[i][j];
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // Выводим элементы матрицы
                    System.out.printf("%3d", new_matrix[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }else{
            System.out.println("Это не матрица");
        }

    }
}
