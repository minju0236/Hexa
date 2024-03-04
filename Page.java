import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
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

    public Page() {
        setTitle("HEXA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        setContentPane(panel);
        setSize(500,800);
        setVisible(true);

        mainPanel.setLayout(new FlowLayout());
//        mainPanel.setBackground(new Color(25, 254, 200));
        mainPanel.setPreferredSize(new Dimension(400,750));
        panel.add(mainPanel);

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
//        System.out.println("==============================================================");
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[j].length; j++) {
                blankPanel[i][j].setShape(data[i][j]);
            }
//            System.out.println("");
        }

        mainPanel.setVisible(true);
        mainPanel.repaint();
    }

    public void createRandom() {
        Random random = new Random();

        // 테스트 데이터
        data[0][3] = random.nextInt(3) + 1; // 1, 2, 3 중 난수 생성
        data[1][3] = random.nextInt(3) + 1;
        data[2][3] = random.nextInt(3) + 1;

        curCol = 3;
        curRow = 2;
    }

    // timer 함수
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                data[curRow+1][curCol] = data[curRow][curCol];
                data[curRow][curCol] = data[curRow-1][curCol];
                data[curRow-1][curCol] = data[curRow-2][curCol];
                data[curRow-2][curCol] = 0;
                curRow++;
                printData();
                print();

                if(curRow == ROW_MAX || data[curRow+1][curCol]!=0) { // 바닥인 경우 또는 curRow행 바로 밑에 행이 0이 아닌 경우
                    //timer.cancel();

                    delete();

                    // 1초 sleep 추가 (1)
                    createRandom();
                }
            }

        };
        timer.schedule(task,2000,1000); // 1초마다
    }

    public void delete() {
        // 삭제 로직 추가 (3)
        for(int i = ROW_MAX; i > 2; i--) { //열,13,세로
            for(int j = 0; j < COL_MAX-2; j++) { //행,7,가로
//                System.out.println(data[i][j]);

                //여기는 한번밖에 안도는 코드임 수정해야함

                //값이 가로로 3개가 같으면
                if(data[i][j]==data[i][j+1] && data[i][j+1]==data[i][j+2]) {
                    data[i][j] = data[i][j+1] = data[i][j+2] = 0;
                    for(int a = i; a>2; a--) {
                        data[a][j] = data[a-1][j];
                        data[a][j+1] = data[a-1][j+1];
                        data[a][j+2] = data[a-1][j+2];
                    }
                }

                //값이 세로로 3개가 같으면
                if(data[i][j]==data[i-1][j] && data[i-1][j]==data[i-2][j]) {
                    data[i][j] = data[i-1][j] = data[i-2][j] = 0;

                    data[i][j] = data[i-3][j]; // 위에서 아래로 공백 없애기
                    for(int a = i-3; a>2; a--) {
                        data[a][j] = data[a-3][j];
                    }
                }

                //값이 대각선(/)로 3개가 같으면
                if(data[i][j]==data[i-1][j] && data[i-1][j]==data[i-2][j]) {
                    data[i][j] = data[i-1][j] = data[i-2][j] = 0;

                    data[i][j] = data[i-3][j]; // 위에서 아래로 공백 없애기
                    for(int a = i-3; a>2; a--) {
                        data[a][j] = data[a-3][j];
                    }
                }

                //값이 대각선(|)로 3개가 같으면
                if(data[i][j]==data[i-1][j] && data[i-1][j]==data[i-2][j]) {
                    data[i][j] = data[i-1][j] = data[i-2][j] = 0;

                    data[i][j] = data[i-3][j]; // 위에서 아래로 공백 없애기
                    for(int a = i-3; a>2; a--) {
                        data[a][j] = data[a-3][j];
                    }
                }
            }
        }
        print();
    }

    public void moveLeft() {
        if(curCol>0 && data[curRow][curCol-1]==0) { // 맨왼쪽 아니거나 바로 왼쪽값 3개가 모두 0인 경우
            // 맨 밑의값의 왼쪽이 빈값이면 됨
            // 3개도 추가 이동
            data[curRow][curCol-1] = data[curRow][curCol];
            data[curRow-1][curCol-1] = data[curRow-1][curCol];
            data[curRow-2][curCol-1] = data[curRow-2][curCol];
            data[curRow][curCol] = 0;
            data[curRow-1][curCol] = 0;
            data[curRow-2][curCol] = 0;
            curCol--;
        }
    }

    public void moveRight() {
        if(curCol<COL_MAX && data[curRow][curCol+1]==0) { // 맨오른쪽이 아니거나 바로 오른쪽값 3개가 모두 0인 경우
            // 3개도 추가 이동
            data[curRow][curCol+1] = data[curRow][curCol];
            data[curRow-1][curCol+1] = data[curRow-1][curCol];
            data[curRow-2][curCol+1] = data[curRow-2][curCol];
            data[curRow][curCol] = 0;
            data[curRow-1][curCol] = 0;
            data[curRow-2][curCol] = 0;
            curCol++;
        }
    }

    public void moveUp() {
        // 3개의 값만 자리 이동
        int n = data[curRow-2][curCol]; // n에 맨 위의 값 저장
        data[curRow-2][curCol] = data[curRow-1][curCol]; // 맨 위 값을 변경 중간 값으로 변경
        data[curRow-1][curCol] = data[curRow][curCol]; // 중간 값을 맨 아래 값으로 변경
        data[curRow][curCol] = n; // 맨 아래 값을 처음 맨 위 변경
    }

    public void moveDown() {
        // 3개를 값이 0인 맨아래 위치로 이동
        // 맨밑일때 누르면 타이머랑 겹쳐서 문제생김
        if(data[curRow+1][curCol]==0 && curRow<ROW_MAX) {
            data[curRow+1][curCol] = data[curRow][curCol];
            data[curRow][curCol] = data[curRow-1][curCol];
            data[curRow-1][curCol] = data[curRow-2][curCol];
            data[curRow-2][curCol] = 0;
            curRow++;
        }
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



