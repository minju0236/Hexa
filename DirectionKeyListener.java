import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DirectionKeyListener implements KeyListener {
    private Page page; // (2) Page와 DirectionKeyListener를 연결

    public DirectionKeyListener(Page page) {
        this.page = page;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            page.moveLeft();
            break;
        case KeyEvent.VK_RIGHT:
            page.moveRight();
            break;
        case KeyEvent.VK_UP:
            page.moveUp();
            break;
        case KeyEvent.VK_DOWN:
            page.moveDown();
            break;
        }
        page.print();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }


}
