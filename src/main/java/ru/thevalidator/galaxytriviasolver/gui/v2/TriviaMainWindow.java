/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.gui.v2;

import com.beust.jcommander.JCommander;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.account.User;
import ru.thevalidator.galaxytriviasolver.account.UserStorage;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.gui.statistic.Statistic;
import ru.thevalidator.galaxytriviasolver.util.identity.Identifier;
import ru.thevalidator.galaxytriviasolver.util.identity.os.OSValidator;
import ru.thevalidator.galaxytriviasolver.util.identity.uuid.UUIDUtil;
import ru.thevalidator.galaxytriviasolver.options.TriviaArgument;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import static ru.thevalidator.galaxytriviasolver.module.trivia.GameResult.DRAW;
import static ru.thevalidator.galaxytriviasolver.module.trivia.GameResult.WIN;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaUserStatsData;
import ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.impl.SolverImpl;
import ru.thevalidator.galaxytriviasolver.notification.Observer;
import ru.thevalidator.galaxytriviasolver.options.ChromeDriverArgument;
import ru.thevalidator.galaxytriviasolver.remote.Connector;
import ru.thevalidator.galaxytriviasolver.service.Task;
import ru.thevalidator.galaxytriviasolver.service.impl.AdvancedTaskImpl;
import ru.thevalidator.galaxytriviasolver.util.formatter.DateTimeFormatter;
import ru.thevalidator.galaxytriviasolver.util.formatter.impl.DateTimeFormatterForLogConsole;
import ru.thevalidator.galaxytriviasolver.web.Locale;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TriviaMainWindow extends javax.swing.JFrame implements Observer {

    public static final String PERSONAL_CODE = Identifier.readKey();
    private static final Logger logger = LogManager.getLogger(TriviaMainWindow.class);
    private static final int MAX_LINES = 1_000;

    private UserStorage userStorage;
    private Statistic stats;
    private final State state;
    private Task task;
    private SwingWorker worker;
    private final DateTimeFormatter formatter;
    private boolean isUtilityMode = false;

    public TriviaMainWindow(TriviaArgument triviaArgs, ChromeDriverArgument chromeArgs) {
        formatter = new DateTimeFormatterForLogConsole();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/trivia.png")));
        this.userStorage = new UserStorage();
        stats = new Statistic();
        state = new State();
        state.setTriviaArgs(triviaArgs);
        state.setChromeArgs(chromeArgs);
        task = null;
        initComponents();
        setLocale();
        setStrategy();
        checkSubscriptionStatus();
        if (triviaArgs.isUtilityMode()) {
            setUtilityAppMode();
        }
    }

    @Override
    public void onUpdateRecieve(String message) {
        appendToPane(message);
    }

    @Override
    public void onGameResultUpdateRecieve(GameResult result, int points, TriviaUserStatsData data) {
        switch (result) {
            case WIN -> {
                appendToPane("WIN (+" + points + ")");
                stats.incrementWin();
                stats.addPoints(points);
                winValueLabel.setText(String.valueOf(stats.getWinCount()));
            }
            case DRAW -> {
                appendToPane("DRAW");
                stats.incrementDraw();
                drawValueLabel.setText(String.valueOf(stats.getDrawCount()));
            }
            default -> {
                appendToPane("LOST");
                stats.incrementLost();
                lostValueLabel.setText(String.valueOf(stats.getLostCount()));
            }
        }
        
        totalGamesValueLabel.setText(String.valueOf(stats.getTotalGamesPlayed()));
        averagePointsValueLabel.setText(String.valueOf(stats.getAveragePoints()));
        actualPointsValueLabel.setText(String.valueOf(data.getUserDailyPoints()));
        actualCoinsValueLabel.setText(String.valueOf(data.getUserCoins()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        strategyTypeButtonGroup = new javax.swing.ButtonGroup();
        autoSrategyButtonGroup = new javax.swing.ButtonGroup();
        backgroundPanel = new BackgroundPanel();
        personPanel = new javax.swing.JPanel();
        personComboBox = new javax.swing.JComboBox<>();
        topicComboBox = new javax.swing.JComboBox<>();
        languageServerComboBox = new javax.swing.JComboBox<>();
        personLabel = new javax.swing.JLabel();
        topicLabel = new javax.swing.JLabel();
        serverLabel = new javax.swing.JLabel();
        addPersonButton = new javax.swing.JButton();
        deletePersonButton = new javax.swing.JButton();
        infoPanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        logLabel = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        statsLabel = new javax.swing.JLabel();
        winLabel = new javax.swing.JLabel();
        winValueLabel = new javax.swing.JLabel();
        drawLabel = new javax.swing.JLabel();
        drawValueLabel = new javax.swing.JLabel();
        lostLabel = new javax.swing.JLabel();
        lostValueLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        totalGamesValueLabel = new javax.swing.JLabel();
        totalGamesLabel = new javax.swing.JLabel();
        averagePointsLabel = new javax.swing.JLabel();
        averagePointsValueLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        actualCoinsLabel = new javax.swing.JLabel();
        actualCoinsValueLabel = new javax.swing.JLabel();
        actualPointsLabel = new javax.swing.JLabel();
        actualPointsValueLabel = new javax.swing.JLabel();
        resetStatsButton = new javax.swing.JButton();
        controlButtonsPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        hardStopButton = new javax.swing.JButton();
        strategyModePanel = new javax.swing.JPanel();
        autoStrategyRadioButton = new javax.swing.JRadioButton();
        manualStrategyRadioButton = new javax.swing.JRadioButton();
        unlimHoursLabel = new javax.swing.JLabel();
        unlimMinutesLabel = new javax.swing.JLabel();
        unlimHoursValueComboBox = new javax.swing.JComboBox<>();
        unlimMinutesValueComboBox = new javax.swing.JComboBox<>();
        unlimTotalPriceLabel = new javax.swing.JLabel();
        unlimTotalPriceValueLabel = new javax.swing.JLabel();
        unlimApproxPointsLabel = new javax.swing.JLabel();
        unlimApproxPointsValueLabel = new javax.swing.JLabel();
        headlessModeCheckBox = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        checkStatusMenuItem = new javax.swing.JMenuItem();
        codeMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        triviaMenu = new javax.swing.JMenu();
        anonymModeCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        strategyMenu = new javax.swing.JMenu();
        passiveModeCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        maxUnlimOnlyCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        getOnTopRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        stayInTopRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        usePointsDeltaCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        setPointsDeltaMenuItem = new javax.swing.JMenuItem();
        advancedMenu = new javax.swing.JMenu();
        ridesMenu = new javax.swing.JMenu();
        playRidesCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        setNosDelayMenuItem = new javax.swing.JMenuItem();
        showNosDelayMenuItem = new javax.swing.JMenuItem();
        humanImitationCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trivia solver");
        setMinimumSize(new java.awt.Dimension(750, 422));
        setResizable(false);
        setType(isUtilityMode ? Window.Type.UTILITY : Window.Type.NORMAL);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        backgroundPanel.setPreferredSize(new java.awt.Dimension(750, 422));
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        personPanel.setOpaque(false);

        personComboBox.setMaximumRowCount(20);
        personComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userStorage.getUserNames()));
        personComboBox.setPreferredSize(new java.awt.Dimension(72, 30));

        topicComboBox.setMaximumRowCount(20);
        topicComboBox.setPreferredSize(new java.awt.Dimension(72, 30));

        languageServerComboBox.setMaximumRowCount(4);
        languageServerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RU", "EN", "ES", "PT" }));
        languageServerComboBox.setPreferredSize(new java.awt.Dimension(72, 30));
        languageServerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageServerComboBoxActionPerformed(evt);
            }
        });

        personLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        personLabel.setForeground(new java.awt.Color(204, 204, 204));
        personLabel.setText("Person");

        topicLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        topicLabel.setForeground(new java.awt.Color(204, 204, 204));
        topicLabel.setText("Topic");

        serverLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        serverLabel.setForeground(new java.awt.Color(204, 204, 204));
        serverLabel.setText("Server");

        addPersonButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addPersonButton.setText("+");
        addPersonButton.setToolTipText("Add person");
        addPersonButton.setFocusPainted(false);
        addPersonButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addPersonButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        addPersonButton.setMaximumSize(new java.awt.Dimension(24, 24));
        addPersonButton.setPreferredSize(new java.awt.Dimension(24, 24));
        addPersonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPersonButtonActionPerformed(evt);
            }
        });

        deletePersonButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deletePersonButton.setText("-");
        deletePersonButton.setToolTipText("Remove person");
        deletePersonButton.setFocusPainted(false);
        deletePersonButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deletePersonButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        deletePersonButton.setMaximumSize(new java.awt.Dimension(24, 24));
        deletePersonButton.setPreferredSize(new java.awt.Dimension(24, 24));
        deletePersonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePersonButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personPanelLayout = new javax.swing.GroupLayout(personPanel);
        personPanel.setLayout(personPanelLayout);
        personPanelLayout.setHorizontalGroup(
            personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personPanelLayout.createSequentialGroup()
                        .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(languageServerComboBox, 0, 1, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, personPanelLayout.createSequentialGroup()
                                .addComponent(addPersonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deletePersonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personPanelLayout.createSequentialGroup()
                        .addComponent(serverLabel)
                        .addGap(54, 54, 54)))
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topicLabel)
                    .addComponent(personLabel)
                    .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(personComboBox, 0, 220, Short.MAX_VALUE)
                        .addComponent(topicComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        personPanelLayout.setVerticalGroup(
            personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(personLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(personComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPersonButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletePersonButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topicLabel)
                    .addComponent(serverLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(languageServerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        backgroundPanel.add(personPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, 129));

        infoPanel.setOpaque(false);

        logTextArea.setColumns(20);
        logTextArea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        logTextArea.setLineWrap(true);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        logLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logLabel.setForeground(new java.awt.Color(204, 204, 204));
        logLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logLabel.setText("LOG CONSOLE");

        statsPanel.setOpaque(false);

        statsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        statsLabel.setForeground(new java.awt.Color(204, 204, 204));
        statsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statsLabel.setText("STATISTICS");

        winLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        winLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        winLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        winLabel.setText("WIN:");

        winValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        winValueLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        winValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        winValueLabel.setText("-");

        drawLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        drawLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
        drawLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        drawLabel.setText("DRAW:");

        drawValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        drawValueLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
        drawValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        drawValueLabel.setText("-");

        lostLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lostLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        lostLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lostLabel.setText("LOST:");

        lostValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lostValueLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        lostValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lostValueLabel.setText("-");

        totalGamesValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        totalGamesValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalGamesValueLabel.setText("-");

        totalGamesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        totalGamesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalGamesLabel.setText("TOTAL GAMES:");

        averagePointsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        averagePointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        averagePointsLabel.setText("AVG POINTS:");

        averagePointsValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        averagePointsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        averagePointsValueLabel.setText("-");

        actualCoinsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        actualCoinsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        actualCoinsLabel.setText("USER COINS:");

        actualCoinsValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        actualCoinsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        actualCoinsValueLabel.setText("-");

        actualPointsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        actualPointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        actualPointsLabel.setText("DAILY POINTS:");

        actualPointsValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        actualPointsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        actualPointsValueLabel.setText("-");

        resetStatsButton.setText("Reset");
        resetStatsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetStatsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statsPanelLayout = new javax.swing.GroupLayout(statsPanel);
        statsPanel.setLayout(statsPanelLayout);
        statsPanelLayout.setHorizontalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(statsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(winLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(winValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                        .addComponent(drawLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(drawValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(totalGamesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalGamesValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                        .addComponent(averagePointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(averagePointsValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(lostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lostValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                        .addComponent(actualPointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualPointsValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addComponent(actualCoinsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualCoinsValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(statsPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
            .addGroup(statsPanelLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(resetStatsButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        statsPanelLayout.setVerticalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalGamesLabel)
                    .addComponent(totalGamesValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(averagePointsLabel)
                    .addComponent(averagePointsValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winLabel)
                    .addComponent(winValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(drawLabel)
                    .addComponent(drawValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lostLabel)
                    .addComponent(lostValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualPointsLabel)
                    .addComponent(actualPointsValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualCoinsLabel)
                    .addComponent(actualCoinsValueLabel))
                .addGap(18, 18, 18)
                .addComponent(resetStatsButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(logLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(logLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        backgroundPanel.add(infoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 144, 738, -1));

        controlButtonsPanel.setOpaque(false);

        startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        startButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        startButton.setForeground(new java.awt.Color(0, 0, 0));
        startButton.setText("GO");
        startButton.setFocusPainted(false);
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setMargin(new java.awt.Insets(2, 14, 2, 14));
        startButton.setMaximumSize(new java.awt.Dimension(99, 50));
        startButton.setMinimumSize(new java.awt.Dimension(99, 22));
        startButton.setPreferredSize(new java.awt.Dimension(99, 50));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        hardStopButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Objects.RedStatus"));
        hardStopButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hardStopButton.setText("HARD STOP");
        hardStopButton.setEnabled(false);
        hardStopButton.setFocusPainted(false);
        hardStopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hardStopButton.setMargin(new java.awt.Insets(2, 14, 2, 14));
        hardStopButton.setMaximumSize(new java.awt.Dimension(99, 50));
        hardStopButton.setPreferredSize(new java.awt.Dimension(99, 50));
        hardStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardStopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlButtonsPanelLayout = new javax.swing.GroupLayout(controlButtonsPanel);
        controlButtonsPanel.setLayout(controlButtonsPanelLayout);
        controlButtonsPanelLayout.setHorizontalGroup(
            controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hardStopButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        controlButtonsPanelLayout.setVerticalGroup(
            controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlButtonsPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hardStopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        hardStopButton.setVisible(false);

        backgroundPanel.add(controlButtonsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 6, -1, -1));

        strategyModePanel.setOpaque(false);
        strategyModePanel.setPreferredSize(new java.awt.Dimension(225, 95));
        strategyModePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        strategyTypeButtonGroup.add(autoStrategyRadioButton);
        autoStrategyRadioButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        autoStrategyRadioButton.setForeground(new java.awt.Color(204, 204, 204));
        autoStrategyRadioButton.setSelected(true);
        autoStrategyRadioButton.setText("AUTO");
        autoStrategyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strategyRadioButtonActionPerformed(evt);
            }
        });
        strategyModePanel.add(autoStrategyRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 0, -1, 22));

        strategyTypeButtonGroup.add(manualStrategyRadioButton);
        manualStrategyRadioButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        manualStrategyRadioButton.setForeground(new java.awt.Color(204, 204, 204));
        manualStrategyRadioButton.setText("MANUAL");
        manualStrategyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualStrategyRadioButtonActionPerformed(evt);
            }
        });
        strategyModePanel.add(manualStrategyRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 0, -1, -1));

        unlimHoursLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimHoursLabel.setForeground(new java.awt.Color(204, 204, 204));
        unlimHoursLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unlimHoursLabel.setText("H:");
        unlimHoursLabel.setMaximumSize(new java.awt.Dimension(25, 16));
        unlimHoursLabel.setMinimumSize(new java.awt.Dimension(25, 16));
        unlimHoursLabel.setPreferredSize(new java.awt.Dimension(25, 16));
        strategyModePanel.add(unlimHoursLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 33, -1, -1));

        unlimMinutesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimMinutesLabel.setForeground(new java.awt.Color(204, 204, 204));
        unlimMinutesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unlimMinutesLabel.setText("M:");
        unlimMinutesLabel.setMaximumSize(new java.awt.Dimension(25, 16));
        unlimMinutesLabel.setMinimumSize(new java.awt.Dimension(25, 16));
        unlimMinutesLabel.setPreferredSize(new java.awt.Dimension(25, 16));
        strategyModePanel.add(unlimMinutesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 33, -1, -1));

        unlimHoursValueComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 }));
        unlimHoursValueComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unlimTimeComboBoxActionPerformed(evt);
            }
        });
        strategyModePanel.add(unlimHoursValueComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 55, -1));

        unlimMinutesValueComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new Integer[] { 0, 30 }));
        unlimMinutesValueComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unlimTimeComboBoxActionPerformed(evt);
            }
        });
        strategyModePanel.add(unlimMinutesValueComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 55, -1));

        unlimTotalPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimTotalPriceLabel.setForeground(new java.awt.Color(170, 170, 170));
        unlimTotalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unlimTotalPriceLabel.setText("PRICE:");
        strategyModePanel.add(unlimTotalPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 60, 60, -1));

        unlimTotalPriceValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimTotalPriceValueLabel.setForeground(new java.awt.Color(170, 170, 170));
        unlimTotalPriceValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        unlimTotalPriceValueLabel.setText("0");
        strategyModePanel.add(unlimTotalPriceValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 55, -1));

        unlimApproxPointsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimApproxPointsLabel.setForeground(new java.awt.Color(170, 170, 170));
        unlimApproxPointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unlimApproxPointsLabel.setText("APPROX POINTS:");
        strategyModePanel.add(unlimApproxPointsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 78, 120, -1));

        unlimApproxPointsValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        unlimApproxPointsValueLabel.setForeground(new java.awt.Color(170, 170, 170));
        unlimApproxPointsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        unlimApproxPointsValueLabel.setText("-");
        strategyModePanel.add(unlimApproxPointsValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 78, 75, -1));

        backgroundPanel.add(strategyModePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(367, 43, -1, -1));

        headlessModeCheckBox.setText("headless");
        state.getChromeArgs().setIsHeadlessMode(headlessModeCheckBox.isSelected());
        headlessModeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headlessModeCheckBoxActionPerformed(evt);
            }
        });
        backgroundPanel.add(headlessModeCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(384, 17, 85, -1));

        mainMenu.setText("Menu");

        checkStatusMenuItem.setText("Check status");
        checkStatusMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStatusMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(checkStatusMenuItem);

        codeMenuItem.setText("Show code");
        codeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(codeMenuItem);

        menuBar.add(mainMenu);

        optionsMenu.setText("Options");

        triviaMenu.setText("Trivia");

        anonymModeCheckBoxMenuItem.setSelected(true);
        anonymModeCheckBoxMenuItem.setText("Play anonymous");
        anonymModeCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anonymModeCheckBoxMenuItemActionPerformed(evt);
            }
        });
        state.setIsAnonymous(true);
        triviaMenu.add(anonymModeCheckBoxMenuItem);

        strategyMenu.setText("Strategy");

        passiveModeCheckBoxMenuItem.setSelected(true);
        passiveModeCheckBoxMenuItem.setText("Passive mode");
        state.setIsPassive(passiveModeCheckBoxMenuItem.isSelected());
        passiveModeCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passiveModeCheckBoxMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(passiveModeCheckBoxMenuItem);

        maxUnlimOnlyCheckBoxMenuItem.setText("Max unlim only");
        state.setIsMaxUnlimOnly(false);
        maxUnlimOnlyCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxUnlimOnlyCheckBoxMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(maxUnlimOnlyCheckBoxMenuItem);

        autoSrategyButtonGroup.add(getOnTopRadioButtonMenuItem);
        getOnTopRadioButtonMenuItem.setText("Get on TOP");
        getOnTopRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getOnTopRadioButtonMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(getOnTopRadioButtonMenuItem);

        autoSrategyButtonGroup.add(stayInTopRadioButtonMenuItem);
        stayInTopRadioButtonMenuItem.setSelected(true);
        state.setShouldStayInTop(stayInTopRadioButtonMenuItem.isSelected());
        stayInTopRadioButtonMenuItem.setText("Stay in TOP");
        stayInTopRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stayInTopRadioButtonMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(stayInTopRadioButtonMenuItem);

        usePointsDeltaCheckBoxMenuItem.setSelected(true);
        usePointsDeltaCheckBoxMenuItem.setText("Use points delta");
        usePointsDeltaCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usePointsDeltaCheckBoxMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(usePointsDeltaCheckBoxMenuItem);

        setPointsDeltaMenuItem.setText("Set points delta");
        setPointsDeltaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPointsDeltaMenuItemActionPerformed(evt);
            }
        });
        strategyMenu.add(setPointsDeltaMenuItem);

        triviaMenu.add(strategyMenu);

        optionsMenu.add(triviaMenu);

        advancedMenu.setText("Advanced");

        ridesMenu.setText("Rides");

        playRidesCheckBoxMenuItem.setText("Play rides");
        state.setShouldPlayRides(playRidesCheckBoxMenuItem.isSelected());
        playRidesCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playRidesCheckBoxMenuItemActionPerformed(evt);
            }
        });
        ridesMenu.add(playRidesCheckBoxMenuItem);

        setNosDelayMenuItem.setText("Set NOS delay");
        setNosDelayMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setNosDelayMenuItemActionPerformed(evt);
            }
        });
        ridesMenu.add(setNosDelayMenuItem);

        showNosDelayMenuItem.setText("Show NOS delay");
        showNosDelayMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showNosDelayMenuItemActionPerformed(evt);
            }
        });
        ridesMenu.add(showNosDelayMenuItem);

        advancedMenu.add(ridesMenu);

        humanImitationCheckBoxMenuItem.setText("Human imitation");
        humanImitationCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                humanImitationCheckBoxMenuItemActionPerformed(evt);
            }
        });
        advancedMenu.add(humanImitationCheckBoxMenuItem);

        optionsMenu.add(advancedMenu);

        menuBar.add(optionsMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void languageServerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageServerComboBoxActionPerformed
        setLocale();
    }//GEN-LAST:event_languageServerComboBoxActionPerformed

    private void strategyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strategyRadioButtonActionPerformed
        setStrategy();
    }//GEN-LAST:event_strategyRadioButtonActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        Component component = new JLabel();
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTextArea jTextArea = new JTextArea(
                "\n"
                + "Galaxy Trivia solver helps you to win in the \n"
                + "Trivia game and get into the daily top 10 list.\n\n"
                + "For use select person (add new if no persons), \n"
                + "choose server and topic you want to play \n"
                + "then click start button.\n"
                + "\n\n"
                + "v1.0.5.0-PVT\n"
                + "[thevalidator]\n"
                + "2023, May"
                + "\n\nRunning on " + OSValidator.OS_NAME + "\n"
                + "Powered by Java\n"
                + "Photo by Andrew Kliatskyi on Unsplash.com");
        jTextArea.setColumns(30);
        jTextArea.setLineWrap(true);
        jTextArea.setRows(16);
        jTextArea.setEditable(false);
        jScrollPane.setViewportView(jTextArea);
        JLabel header = new JLabel();
        header.setText("Trivia solver");
        header.setFont(new java.awt.Font("Segoe UI", 1, 14));
        header.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        header.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jScrollPane.setColumnHeaderView(header);
        JOptionPane.showMessageDialog(this, jScrollPane, "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }//GEN-LAST:event_formWindowClosing

    private void unlimTimeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unlimTimeComboBoxActionPerformed
        updateUnlimInfoLabels();
    }//GEN-LAST:event_unlimTimeComboBoxActionPerformed

    private void checkStatusMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkStatusMenuItemActionPerformed
        checkSubscriptionStatus();
    }//GEN-LAST:event_checkStatusMenuItemActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (task == null || !task.isRunning()) {
            if (!PERSONAL_CODE.equals(Identifier.ERROR_KEY)) {
                if (userStorage.getUsers().isEmpty()) {
                    appendToPane("ERROR: NO PERSON(S)");
                    return;
                }
//                configureState();
//                System.out.println(state.toString());
//                System.out.println("======");
                startTask();
            } else {
                appendToPane("ERROR: NO REGISTERED USER KEY DATA");
            }
        } else {
            appendToPane("Will stop after finishing playing");
            stopTask();
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void hardStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardStopButtonActionPerformed
        if (worker != null && !worker.isDone()) {
            task.interrupt();
            worker.cancel(true);

            personComboBox.setEnabled(true);
            topicComboBox.setEnabled(true);
        }
    }//GEN-LAST:event_hardStopButtonActionPerformed

    private void headlessModeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headlessModeCheckBoxActionPerformed
        if (headlessModeCheckBox.isSelected()) {
            state.getChromeArgs().setIsHeadlessMode(true);
            appendToPane("HEADLESS MODE ON");
        } else {
            state.getChromeArgs().setIsHeadlessMode(false);
            appendToPane("HEADLESS MODE OFF");
        }
    }//GEN-LAST:event_headlessModeCheckBoxActionPerformed

    private void deletePersonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePersonButtonActionPerformed
        JPanel panel = new JPanel(new GridLayout(0, 1));
        int selectedPersonImdex = personComboBox.getSelectedIndex();
        User user = userStorage.getUser(selectedPersonImdex);
        panel.add(new JLabel("Remove '" + user.getName() + "' ?"));

        int result = JOptionPane.showConfirmDialog(null, panel, "Remove person",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                List<User> userList = userStorage.getUsers();
                userList.remove(selectedPersonImdex);
                userStorage = new UserStorage(userList);
                UserStorage.writeUserData(userList);
                personComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userStorage.getUserNames()));
            } catch (Exception e) {
                String message;
                if (e instanceof IllegalArgumentException) {
                    message = e.getMessage();
                } else {
                    message = "Remove person error!";
                    logger.error(ExceptionUtil.getFormattedDescription(e));
                }
                appendToPane(message);
            }
        }
    }//GEN-LAST:event_deletePersonButtonActionPerformed

    private void addPersonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPersonButtonActionPerformed
        JTextField nameField = new JTextField();
        JTextField codeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name (any you want):"));
        panel.add(nameField);
        panel.add(new JLabel("Code:"));
        panel.add(codeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add person",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                List<User> userList = userStorage.getUsers();
                String name = nameField.getText().trim();
                String code = codeField.getText().trim();
                User u = new User(name, code);
                userList.add(u);

                userStorage = new UserStorage(userList);
                UserStorage.writeUserData(userList);
                personComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userStorage.getUserNames()));
            } catch (Exception e) {
                String message;
                if (e instanceof IllegalArgumentException) {
                    message = e.getMessage();
                } else {
                    message = "Add person error!";
                    logger.error(ExceptionUtil.getFormattedDescription(e));
                }
                appendToPane(message);
            }
        }
    }//GEN-LAST:event_addPersonButtonActionPerformed

    private void codeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeMenuItemActionPerformed
        showPersonalCode();
    }//GEN-LAST:event_codeMenuItemActionPerformed

    private void anonymModeCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anonymModeCheckBoxMenuItemActionPerformed
        if (anonymModeCheckBoxMenuItem.isSelected()) {
            appendToPane("ANONYMOUS MODE ON");
            state.setIsAnonymous(true);
        } else {
            appendToPane("ANONYMOUS MODE OFF");
            state.setIsAnonymous(false);
        }
    }//GEN-LAST:event_anonymModeCheckBoxMenuItemActionPerformed

    private void humanImitationCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humanImitationCheckBoxMenuItemActionPerformed
        if (humanImitationCheckBoxMenuItem.isSelected()) {
            appendToPane("HUMAN IMITATION MODE ON");
            state.setIsAnonymous(true);
        } else {
            appendToPane("HUMAN IMITATION MODE OFF");
            state.setIsAnonymous(false);
        }
    }//GEN-LAST:event_humanImitationCheckBoxMenuItemActionPerformed

    private void playRidesCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playRidesCheckBoxMenuItemActionPerformed
        if (playRidesCheckBoxMenuItem.isSelected()) {
            appendToPane("PLAY RIDES MODE ON");
            state.setShouldPlayRides(true);
        } else {
            appendToPane("PLAY RIDES MODE OFF");
            state.setShouldPlayRides(false);
        }
    }//GEN-LAST:event_playRidesCheckBoxMenuItemActionPerformed

    private void showNosDelayMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showNosDelayMenuItemActionPerformed
        appendToPane("NOS delay: " + state.getNosDelayTime() + " ms");
    }//GEN-LAST:event_showNosDelayMenuItemActionPerformed

    private void stayInTopRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stayInTopRadioButtonMenuItemActionPerformed
        setAutoStrategyMode();
    }//GEN-LAST:event_stayInTopRadioButtonMenuItemActionPerformed

    private void getOnTopRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getOnTopRadioButtonMenuItemActionPerformed
        setAutoStrategyMode();
    }//GEN-LAST:event_getOnTopRadioButtonMenuItemActionPerformed

    private void manualStrategyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualStrategyRadioButtonActionPerformed
        strategyRadioButtonActionPerformed(evt);
    }//GEN-LAST:event_manualStrategyRadioButtonActionPerformed

    private void passiveModeCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passiveModeCheckBoxMenuItemActionPerformed
        if (passiveModeCheckBoxMenuItem.isSelected()) {
            appendToPane("PASSIVE MODE ON");
            state.setIsPassive(true);
        } else {
            appendToPane("PASSIVE MODE OFF");
            state.setIsPassive(false);
        }
    }//GEN-LAST:event_passiveModeCheckBoxMenuItemActionPerformed

    private void maxUnlimOnlyCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxUnlimOnlyCheckBoxMenuItemActionPerformed
        if (maxUnlimOnlyCheckBoxMenuItem.isSelected()) {
            appendToPane("BUY MAX UNLIM ONLY MODE ON");
            state.setIsMaxUnlimOnly(true);
        } else {
            appendToPane("BUY MAX UNLIM ONLY MODE OFF");
            state.setIsMaxUnlimOnly(false);
        }
    }//GEN-LAST:event_maxUnlimOnlyCheckBoxMenuItemActionPerformed

    private void resetStatsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetStatsButtonActionPerformed
        stats = new Statistic();
        updateStats();
    }//GEN-LAST:event_resetStatsButtonActionPerformed

    private void usePointsDeltaCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usePointsDeltaCheckBoxMenuItemActionPerformed
        // TODO add your handling code here:
        appendToPane("Not supported yet");
    }//GEN-LAST:event_usePointsDeltaCheckBoxMenuItemActionPerformed

    private void setPointsDeltaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPointsDeltaMenuItemActionPerformed
        // TODO add your handling code here:
        appendToPane("Not supported yet");
    }//GEN-LAST:event_setPointsDeltaMenuItemActionPerformed

    private void setNosDelayMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setNosDelayMenuItemActionPerformed
        // TODO add your handling code here:
        String m = JOptionPane.showInputDialog("Type new NOS delay value (3100-7600)");
        try {
            int newNOSValue = Integer.parseInt(m);
            if (newNOSValue > 3100 && newNOSValue < 7600) {
                this.state.setNosDelayTime(newNOSValue);
                appendToPane("New NOS delay: " + m + " ms");
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            appendToPane("incorrect NOS delay value");
        }
    }//GEN-LAST:event_setNosDelayMenuItemActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        if (isUtilityMode && OSValidator.IS_WINDOWS) {
            this.setVisible(false);
        }
        this.setState(ICONIFIED);
    }//GEN-LAST:event_formWindowIconified

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        TriviaArgument triviaArgs = new TriviaArgument();
        ChromeDriverArgument chromeArgs = new ChromeDriverArgument();
        JCommander commander = JCommander.newBuilder()
                .addObject(triviaArgs)
                .addObject(chromeArgs)
                .build();
        commander.parse(args);

//        System.out.println("> deb " + triviaArgs.hasDebugOption());
//        System.out.println("> adv " + triviaArgs.hasAdvancedSettingsOption());
//        System.out.println("> eml " + triviaArgs.hasCheckMailOption());
//        System.out.println("> lgf " + triviaArgs.hasLogOffOption());
//        System.out.println("> rds " + triviaArgs.hasPlayRidesOption());
//        
//        System.out.println("> ori " + chromeArgs.hasRemoteAllowOriginsOption());
//        System.out.println("> cus " + chromeArgs.getWebdriverCustomPath());
//        System.out.println("> hed " + chromeArgs.isHeadlessMode());
        java.awt.EventQueue.invokeLater(() -> {
            UIManager.put("Button.arc", 15);
            FlatDarkLaf.setup();
            new TriviaMainWindow(triviaArgs, chromeArgs).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel actualCoinsLabel;
    private javax.swing.JLabel actualCoinsValueLabel;
    private javax.swing.JLabel actualPointsLabel;
    private javax.swing.JLabel actualPointsValueLabel;
    private javax.swing.JButton addPersonButton;
    private javax.swing.JMenu advancedMenu;
    private javax.swing.JCheckBoxMenuItem anonymModeCheckBoxMenuItem;
    private javax.swing.ButtonGroup autoSrategyButtonGroup;
    private javax.swing.JRadioButton autoStrategyRadioButton;
    private javax.swing.JLabel averagePointsLabel;
    private javax.swing.JLabel averagePointsValueLabel;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JMenuItem checkStatusMenuItem;
    private javax.swing.JMenuItem codeMenuItem;
    private javax.swing.JPanel controlButtonsPanel;
    private javax.swing.JButton deletePersonButton;
    private javax.swing.JLabel drawLabel;
    private javax.swing.JLabel drawValueLabel;
    private javax.swing.JRadioButtonMenuItem getOnTopRadioButtonMenuItem;
    private javax.swing.JButton hardStopButton;
    private javax.swing.JCheckBox headlessModeCheckBox;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JCheckBoxMenuItem humanImitationCheckBoxMenuItem;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> languageServerComboBox;
    private javax.swing.JLabel logLabel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JLabel lostLabel;
    private javax.swing.JLabel lostValueLabel;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JRadioButton manualStrategyRadioButton;
    private javax.swing.JCheckBoxMenuItem maxUnlimOnlyCheckBoxMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JCheckBoxMenuItem passiveModeCheckBoxMenuItem;
    private javax.swing.JComboBox<String> personComboBox;
    private javax.swing.JLabel personLabel;
    private javax.swing.JPanel personPanel;
    private javax.swing.JCheckBoxMenuItem playRidesCheckBoxMenuItem;
    private javax.swing.JButton resetStatsButton;
    private javax.swing.JMenu ridesMenu;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JMenuItem setNosDelayMenuItem;
    private javax.swing.JMenuItem setPointsDeltaMenuItem;
    private javax.swing.JMenuItem showNosDelayMenuItem;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statsLabel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JRadioButtonMenuItem stayInTopRadioButtonMenuItem;
    private javax.swing.JMenu strategyMenu;
    private javax.swing.JPanel strategyModePanel;
    private javax.swing.ButtonGroup strategyTypeButtonGroup;
    private javax.swing.JComboBox<String> topicComboBox;
    private javax.swing.JLabel topicLabel;
    private javax.swing.JLabel totalGamesLabel;
    private javax.swing.JLabel totalGamesValueLabel;
    private javax.swing.JMenu triviaMenu;
    private javax.swing.JLabel unlimApproxPointsLabel;
    private javax.swing.JLabel unlimApproxPointsValueLabel;
    private javax.swing.JLabel unlimHoursLabel;
    private javax.swing.JComboBox<Integer> unlimHoursValueComboBox;
    private javax.swing.JLabel unlimMinutesLabel;
    private javax.swing.JComboBox<Integer> unlimMinutesValueComboBox;
    private javax.swing.JLabel unlimTotalPriceLabel;
    private javax.swing.JLabel unlimTotalPriceValueLabel;
    private javax.swing.JCheckBoxMenuItem usePointsDeltaCheckBoxMenuItem;
    private javax.swing.JLabel winLabel;
    private javax.swing.JLabel winValueLabel;
    // End of variables declaration//GEN-END:variables

    public void appendToPane(String msg) {
        try {
            String timestamp = formatter.getFormattedDateTime(LocalDateTime.now());
            String line = "[" + timestamp + "] - " + msg + "\n";
            cleanConsole();
            logTextArea.append(line);
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        } catch (Exception e) {
            logger.error("APPEND METHOD: {}", e.getMessage());
        }
    }

    private void cleanConsole() {
        try {
            javax.swing.text.Element root = logTextArea.getDocument().getDefaultRootElement();
            if (root.getElementCount() > MAX_LINES) {
                javax.swing.text.Element firstLine = root.getElement(0);
                logTextArea.getDocument().remove(0, firstLine.getEndOffset());
            }
        } catch (BadLocationException e) {
            logger.error("CLEAN CONSOLE METHOD: {}", e.getMessage());
        }
    }

    private void initLocale(Locale locale) {
        state.setLocale(locale);
        topicComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(locale.getTopics()));
    }

    private void setLocale() {
        String selectedServer = (String) languageServerComboBox.getSelectedItem();
        initLocale(Locale.valueOf(selectedServer));
        appendToPane(selectedServer + " server selected");
    }

    private void setStrategy() {
        boolean b;
        if (strategyTypeButtonGroup.isSelected(autoStrategyRadioButton.getModel())) {
            b = false;
            strategyMenu.setEnabled(true);
            strategyMenu.setVisible(true);
            appendToPane("AUTO strategy enabled");
        } else {
            b = true;
            strategyMenu.setEnabled(false);
            strategyMenu.setVisible(false);
            appendToPane("MANUAL strategy enabled");
        }

        //state.setShouldStayInTop(!b);   //false
        //state.setShouldGetOnTop(!b);    //false
        unlimHoursValueComboBox.setEnabled(b);
        unlimHoursValueComboBox.setVisible(b);
        unlimHoursLabel.setVisible(b);

        unlimMinutesValueComboBox.setEnabled(b);
        unlimMinutesValueComboBox.setVisible(b);
        unlimMinutesLabel.setVisible(b);

        unlimTotalPriceLabel.setVisible(b);
        unlimTotalPriceValueLabel.setVisible(b);

        unlimApproxPointsLabel.setVisible(b);
        unlimApproxPointsValueLabel.setVisible(b);
    }

    private void updateUnlimInfoLabels() {
        int hours = Integer.parseInt(String.valueOf(unlimHoursValueComboBox.getSelectedItem()));
        int minutes = Integer.parseInt(String.valueOf(unlimMinutesValueComboBox.getSelectedItem()));
        int totalMinutes = hours * 60 + minutes;
        double totalPrice = UnlimUtil.getPrice(totalMinutes);
        int approximatelyPoints = UnlimUtil.getApproxPoints(totalMinutes);
        unlimTotalPriceValueLabel.setText(String.valueOf(totalPrice));
        unlimApproxPointsValueLabel.setText(getFormattedNumberString(approximatelyPoints));
    }

    public String getFormattedNumberString(double value) {
        if (value == 0) {
            return "-";
        }
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(value);
    }

    private void checkSubscriptionStatus() {
        int responseCode = 0;
        try {
            responseCode = Connector.getResponseCode(PERSONAL_CODE);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        String result;
        result = switch (responseCode) {
            case HttpStatus.SC_NOT_FOUND ->
                "NO REGISTERED KEY";
            case HttpStatus.SC_MOVED_TEMPORARILY ->
                "THE KEY IS EXPIRED";
            case HttpStatus.SC_OK ->
                "THE KEY IS ACTIVE";
            default ->
                "NO CONNECTION WITH THE SERVER";
        };
        appendToPane("status: " + result);
    }

    private void startTask() {
        try {
            setStartButtonStatus(1);
            configureState();

            if (task == null) {
                Solver solver = new SolverImpl();
                task = new AdvancedTaskImpl(this, solver);
            }
            task.setState(state);

            worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        task.run();

                    } catch (Exception e) {
                        appendToPane(e.getMessage());
                        logger.error(ExceptionUtil.getFormattedDescription(e));
                    }
                    return null;
                }

                @Override
                protected void done() {
                    appendToPane("STOPPED");
                    setStartButtonStatus(-1);
                }
            };
            worker.execute();
        } catch (Throwable e) {
            appendToPane("Internal error, can't start the app!");
            logger.error(ExceptionUtil.getFormattedDescription(e));
            setStartButtonStatus(-1);
        }

    }

    private void stopTask() {
        setStartButtonStatus(0);
        task.stop();
    }

    public void setStartButtonStatus(int status) {
        switch (status) {
            case 1 -> {
                personComboBox.setEnabled(false);
                topicComboBox.setEnabled(false);
                languageServerComboBox.setEnabled(false);
                strategyBlockEnable(false);
                hardStopButton.setEnabled(true);
                hardStopButton.setVisible(true);
                startButton.setEnabled(true);
                startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
                startButton.setText("STOP");
            }
            case -1 -> {
                personComboBox.setEnabled(true);
                topicComboBox.setEnabled(true);
                languageServerComboBox.setEnabled(true);
                strategyBlockEnable(true);
                hardStopButton.setEnabled(false);
                hardStopButton.setVisible(false);
                startButton.setEnabled(true);
                startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
                startButton.setText("START");
            }
            default -> {
                hardStopButton.setEnabled(true);
                hardStopButton.setVisible(true);
                startButton.setEnabled(false);
                startButton.setText("WAIT");
            }
        }
    }

    private void strategyBlockEnable(boolean b) {
        autoStrategyRadioButton.setEnabled(b);
        manualStrategyRadioButton.setEnabled(b);
        unlimHoursValueComboBox.setEnabled(b);
        unlimMinutesValueComboBox.setEnabled(b);
    }

    private void showPersonalCode() {
        try {
            String key = UUIDUtil.getUUID();
            Component component = new JLabel();

            JScrollPane jScrollPane = new JScrollPane(component);
            jScrollPane.setBorder(BorderFactory.createEmptyBorder());
            JTextArea jTextArea = new JTextArea("-==" + key + "==-");

            jTextArea.setColumns(30);
            jTextArea.setLineWrap(true);
            jTextArea.setRows(5);
            jScrollPane.setViewportView(jTextArea);

            JOptionPane.showMessageDialog(this, jScrollPane, "Personal user code", JOptionPane.PLAIN_MESSAGE);

        } catch (HeadlessException | IOException | InterruptedException e) {
            appendToPane("ERROR: can't get the personal user code");
            ExceptionUtil.getFormattedDescription(e);
        }
    }

    private void configureState() {
        User user = userStorage.getUser(personComboBox.getSelectedIndex());
        int topicIndex = topicComboBox.getSelectedIndex();
        state.setUser(user);
        state.setTopicIndex(topicIndex);
        if (manualStrategyRadioButton.isSelected()) {
            state.setIsManualStrategy(true);
//            state.setShouldStayInTop(false);
//            state.setShouldGetOnTop(false);
            int hours = Integer.parseInt(String.valueOf(unlimHoursValueComboBox.getSelectedItem()));
            int minutes = Integer.parseInt(String.valueOf(unlimMinutesValueComboBox.getSelectedItem()));
            state.setUnlimStrategyTime(hours * 60 + minutes);
        } else {
            state.setIsManualStrategy(false);
            setAutoStrategyMode();
//            state.setShouldStayInTop(true);
//            state.setShouldGetOnTop(false);
        }
    }

    private void setAutoStrategyMode() {
        if (autoSrategyButtonGroup.isSelected(stayInTopRadioButtonMenuItem.getModel())) {
            state.setShouldStayInTop(true);
            state.setShouldGetOnTop(false);
            appendToPane("STAY IN TOP mode enabled");
        } else {
            state.setShouldStayInTop(false);
            state.setShouldGetOnTop(true);
            appendToPane("GET ON TOP mode enabled");
        }
    }

    private void updateStats() {
        totalGamesValueLabel.setText(String.valueOf(stats.getTotalGamesPlayed()));
        winValueLabel.setText(String.valueOf(stats.getWinCount()));
        lostValueLabel.setText(String.valueOf(stats.getLostCount()));
        drawValueLabel.setText(String.valueOf(stats.getDrawCount()));
        averagePointsValueLabel.setText(String.valueOf(stats.getAveragePoints()));
    }

    private void setUtilityAppMode() {
        isUtilityMode = true;
        addTrayIcon();
    }

    private void addTrayIcon() {
        if (isUtilityMode && SystemTray.isSupported()) {
            JFrame w = this;
            final PopupMenu popup = new PopupMenu();
            final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tray.png")));
            final SystemTray tray = SystemTray.getSystemTray();

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener((ActionEvent e) -> {
                tray.remove(trayIcon);
                System.exit(0);
            });

            popup.add(exitItem);

            trayIcon.setPopupMenu(popup);

            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        w.setVisible(true);
                        w.setState(NORMAL);
                        w.toFront();
                    } else if (e.getButton() == MouseEvent.BUTTON1) {
                        w.setVisible(false);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                logger.error("Tray icon could not be added: {}", e.getMessage());
            }
        }
    }
}
