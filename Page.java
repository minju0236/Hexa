import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Page extends JFrame {
    final private int COL_SIZE = 7;
    final private int ROW_SIZE = 13;
    final private int COL_MAX = 6;
    final private int ROW_MAX = 12;


    private int[][] data = new int[ROW_SIZE][COL_SIZE];
    private Panel panel = new Panel();
    private JPanel mainPanel = new JPanel();
    private CellPanel[][] blankPanel = new CellPanel[ROW_SIZE][COL_SIZE];
    private int curRow;
    private int curCol;
    private JPanel gameOverPanel = new JPanel();
    private JLabel blank = new JLabel("");
    private JPanel gameOverMentPanel = new JPanel();
    private JLabel gameOverMent = new JLabel("Game Over");

    public Page() {
        setTitle("HEXA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        setContentPane(panel);
        setSize(500,800);
        setVisible(true);

        mainPanel.setLayout(new FlowLayout());
        mainPanel.setPreferredSize(new Dimension(400,750));
        panel.add(mainPanel);

        gameOverPanel.setLayout(new GridLayout(3,1));
        gameOverPanel.setPreferredSize(new Dimension(250,300));
        gameOverPanel.add(this.blank);
        gameOverPanel.add(this.gameOverMentPanel);
        gameOverMentPanel.setLayout(new FlowLayout());
        gameOverMentPanel.add(this.gameOverMent);
        gameOverMent.setFont(new Font("Arial",Font.PLAIN,30));
        panel.add(gameOverPanel);
        gameOverPanel.setVisible(false);

        for(int i = 0; i < ROW_SIZE; i++) {
            for(int j = 0; j < COL_SIZE; j++) {
                data[i][j] = 0;
                blankPanel[i][j] = new CellPanel();
                blankPanel[i][j].setLayout(new GridLayout(5,5));
                blankPanel[i][j].setBackground(new Color(255, 254, 240));
                blankPanel[i][j].setPreferredSize(new Dimension(50,50));
                mainPanel.add(blankPanel[i][j]);
            }
        }

    }

    public void printData() {
      System.out.println("==============================================================");
      for(int i = 0; i < data.length; i++) {
          for(int j = 0; j < data[j].length; j++) {
              System.out.print(data[i][j]);
          }
          System.out.println("");
      }
    }

    // print 함수
    public void print() {
        mainPanel.setVisible(false);
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[j].length; j++) {
                blankPanel[i][j].setShape(data[i][j]);
            }
        }

        mainPanel.setVisible(true);
        mainPanel.repaint();
    }

    public void createRandom() {
        System.out.println("+++ createRandom");
        if(curRow==1) {
            data[0][curCol] = (int)(Math.random()*3) + 1;
        } else if (curRow==2) {
            data[0][curCol] = (int)(Math.random()*3) + 1;
        } else {
            data[0][3] = (int)(Math.random()*3) + 1; // 1, 2, 3 중 난수 생성
            curCol = 3;
            curRow = 0;
        }
    }

    // timer 함수
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                    data[curRow+1][curCol] = data[curRow][curCol];
                    if(curRow >= 1) {
                        data[curRow][curCol] = data[curRow-1][curCol];
                    }
                    if(curRow >= 2) {
                        data[curRow-1][curCol] = data[curRow-2][curCol];
                        data[curRow-2][curCol] = 0;
                    }
                curRow++;
                printData();

                if(curRow == ROW_MAX || data[curRow+1][curCol]!=0) { // 바닥인 경우 또는 curRow행 바로 밑에 행이 0이 아닌 경우
                    boolean deleted = false;

                    do {
                        deleted = delete();
                        // 1초 sleep 추가
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while(deleted == false);


                    boolean gameOver = isGameOver();
                    if(gameOver==true) {
                        // 1초 sleep 추가
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        timer.cancel();
                        System.out.println("gameover");
                        mainPanel.setVisible(false);
                        setContentPane(gameOverPanel);
                        gameOverPanel.setVisible(true);
                    } else {
                        createRandom();
                    }

                } else if(curRow <= 2) {
                    // 1초 sleep 추가
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createRandom();

                }
                print();
            }

        };
        timer.schedule(task,2000,1000); // 1초마다
    }

    public boolean delete() {
        boolean isDel = false;

        // 삭제 로직 추가 (3)
        for(int i = ROW_MAX; i > 0; i--) { //열,13,세로
            for(int j = 0; j < COL_MAX; j++) { //행,7,가로
                //같은 항목에 대해서 2번이상 돌지 않는거 같음(위치가 다르면 도는데 달라진 위치로는 안돌음)!!!!!!!!!!!!!!!
                //3개가 아니라 4개, 5개가 같은 경우 또는 동시에 같으면 3개만 지워짐!!!!!!!!!!!!!!!

                //값이 가로로 3개가 같으면
                if (j <= COL_MAX - 2) {
                    if(data[i][j]==data[i][j+1] && data[i][j+1]==data[i][j+2]) { //3개가 같으면
                        data[i][j] = data[i][j+1] = data[i][j+2] = 0;

                        for(int a = i; a>2; a--) {
                            data[a][j] = data[a-1][j];
                            data[a][j+1] = data[a-1][j+1];
                            data[a][j+2] = data[a-1][j+2];
                        }

                        isDel = true;
                    }
                }

                //값이 세로로 3개가 같으면
                if(i > 2) {
                    if(data[i][j]==data[i-1][j] && data[i-1][j]==data[i-2][j]) {
                        data[i][j] = data[i-1][j] = data[i-2][j] = 0;
                        data[i][j] = data[i-3][j]; // 위에서 아래로 공백 없애기

                        for(int a = i-3; a>2; a--) {
                            data[a][j] = data[a-3][j];
                        }

                        isDel = true;
                    }
                }

                //값이 대각선(/)로 3개가 같으면
                if (i > 2 && j <= COL_MAX - 2) {
                    if(data[i][j] == data[i-1][j+1] && data[i-1][j+1] == data[i-2][j+2]) {
                        data[i][j] = data[i-1][j+1] = data[i-2][j+2] = 0;

                        for(int a = i; a>2; a--) {
                            data[a][j] = data[a-1][j];
                            data[a-1][j+1] = data[a-2][j+1];
                            data[a-2][j+2] = data[a-3][j+2];
                        }

                        isDel = true;
                    }
                }

                //값이 대각선(\)로 3개가 같으면
                if ((i > 2 && 2 <= j) && j <= COL_MAX ) {
                    if(data[i][j] == data[i-1][j-1] && data[i-1][j-1] == data[i-2][j-2]) {
                        data[i][j] = data[i-1][j-1] = data[i-2][j-2] = 0;

                        for(int a = i; a>2; a--) {
                            data[a][j] = data[a-1][j];
                            data[a-1][j-1] = data[a-2][j-1];
                            data[a-2][j-2] = data[a-3][j-2];
                        }

                        isDel = true;
                    }
                }
            }
        }
        print();

        return isDel;
    }

    public void moveLeft() {
        if(curCol>0 && data[curRow][curCol-1]==0) { // 맨왼쪽 아니거나 바로 왼쪽값 3개가 모두 0인 경우
            // 맨 밑의값의 왼쪽이 빈값이면 됨
            // 3개도 추가 이동
            if(curRow>=2) {
                data[curRow][curCol-1] = data[curRow][curCol];
                data[curRow-1][curCol-1] = data[curRow-1][curCol];
                data[curRow-2][curCol-1] = data[curRow-2][curCol];
                data[curRow][curCol] = 0;
                data[curRow-1][curCol] = 0;
                data[curRow-2][curCol] = 0;
                curCol--;
            }
        }
    }

    public void moveRight() {
        if(curCol<COL_MAX && data[curRow][curCol+1]==0) { // 맨오른쪽이 아니거나 바로 오른쪽값 3개가 모두 0인 경우
            // 3개도 추가 이동
            if(curRow>=2) {
                data[curRow][curCol+1] = data[curRow][curCol];
                data[curRow-1][curCol+1] = data[curRow-1][curCol];
                data[curRow-2][curCol+1] = data[curRow-2][curCol];
                data[curRow][curCol] = 0;
                data[curRow-1][curCol] = 0;
                data[curRow-2][curCol] = 0;
                curCol++;
            }
        }
    }

    public void moveUp() {
        // 3개의 값만 자리 이동
        if(curRow>=2) {
            int n = data[curRow-2][curCol]; // n에 맨 위의 값 저장
            data[curRow-2][curCol] = data[curRow-1][curCol]; // 맨 위 값을 변경 중간 값으로 변경
            data[curRow-1][curCol] = data[curRow][curCol]; // 중간 값을 맨 아래 값으로 변경
            data[curRow][curCol] = n; // 맨 아래 값을 처음 맨 위 변경
        }
    }

    public void moveDown() {
        // 3개를 맨아래로 이동
        //curRow+1로 하면 내려오면서 하나 겹침 / ROW_MAX-1 안하면 에러
        if(curRow>=2) {
            while(data[curRow+2][curCol]==0 && curRow<ROW_MAX-1) {
                data[curRow+1][curCol] = data[curRow][curCol];
                data[curRow][curCol] = data[curRow-1][curCol];
                data[curRow-1][curCol] = data[curRow-2][curCol];
                data[curRow-2][curCol] = 0;
                curRow++;
        }
        }
    }

    public boolean isGameOver() {
        //맨 위의 값 4개가 모두 차 있을때 (3개는 도형이 내려오면서 찰 수 있기 때문에)
        if((data[0][curCol]!=0 && data[1][curCol]!=0)&& (data[2][curCol]!=0 && data[3][curCol]!=0)) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Page page = new Page();
        DirectionKeyListener listener = new DirectionKeyListener(page);
        page.addKeyListener(listener);

        page.createRandom();

        page.printData();
        page.timer();
        page.print();
    }

}

//3) 더이상 내려올 곳이 없으면 Game Over 처리
//4) 랜덤 생성된 3개가 처음에 한번에 보이지 않고 1개씩 보이기



