 import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ShapePanel extends JPanel {
    private List<Shape> shapes;
    private Shape currentShape;
    private Point startPoint;
    private int shapeType = 0; // Default shape type is Rectangle

    public ShapePanel() {
        shapes = new ArrayList<>();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.LIGHT_GRAY);

        // Control panel for shape buttons
        JPanel controlPanel = new JPanel(new GridLayout(5, 1));
        String[] shapeNames = {"Rectangle", "Oval", "Triangle", "Line", "Circle"};

        for (int i = 0; i < shapeNames.length; i++) {
            JButton shapeButton = new JButton(shapeNames[i]);
            final int type = i; // Use a final variable for lambda
            shapeButton.addActionListener(e -> setShapeType(type));
            controlPanel.add(shapeButton);
        }

        controlPanel.setBorder(BorderFactory.createTitledBorder("Shapes"));
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.WEST);

        // Mouse listener for shape drawing
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    shapes.add(currentShape);
                    currentShape = null;
                    repaint();
                }
            }
        });

        // Mouse motion listener for shape dragging
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                drawShape(startPoint, e.getPoint());
                repaint();
            }
        });
    }

    private void setShapeType(int type) {
        shapeType = type;
    }

    private void drawShape(Point start, Point end) {
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);

        switch (shapeType) {
            case 0: // Rectangle
                currentShape = new Rectangle(x, y, width, height);
                break;
            case 1: // Oval
                currentShape = new Ellipse2D.Double(x, y, width, height);
                break;
            case 2: // Triangle
                int[] xPoints = {start.x, end.x, (start.x + end.x) / 2};
                int[] yPoints = {start.y, start.y, end.y};
                currentShape = new Polygon(xPoints, yPoints, 3);
                break;
            case 3: // Line
                currentShape = new Line2D.Double(start.x, start.y, end.x, end.y);
                break;
            case 4: // Circle
                int diameter = Math.min(width, height);
                currentShape = new Ellipse2D.Double(x, y, diameter, diameter);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw all shapes from the list
        for (Shape shape : shapes) {
            g2d.setColor(Color.BLUE);
            g2d.draw(shape);
        }

        // Draw the shape currently being drawn
        if (currentShape != null) {
            g2d.setColor(Color.RED);
            g2d.draw(currentShape);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shape Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ShapePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


