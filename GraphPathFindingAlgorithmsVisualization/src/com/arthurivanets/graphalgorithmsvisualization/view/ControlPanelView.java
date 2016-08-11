package com.arthurivanets.graphalgorithmsvisualization.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.arthurivanets.graphalgorithmsvisualization.algorithms.Algorithm;
import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.GraphType;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;
import com.arthurivanets.graphalgorithmsvisualization.model.OptionItem;
import com.arthurivanets.graphalgorithmsvisualization.util.LayoutConstraints;

public class ControlPanelView extends JDialog {
	
	
	private String mDisplayTitle;
	private Dimension mDisplaySize;
	
	private JFrame mParent;
	
	private Algorithm mSelectedAlgorithm;
	private GraphType mSelectedGraphType;
	private MovabilityModel mSelectedMovabilityModel;
	private int mSelectedNodeType;
	
	private JPanel mNodeTypeButtonContainer;
	private JPanel mActionButtonContainer;
	
	private JLabel mAlgorithmsLabel;
	private JLabel mGraphTypeLabel;
	private JLabel mMovabilityModelLabel;
	
	private JComboBox<OptionItem> mAlgorithmPicker;
	private JComboBox<OptionItem> mGraphTypePicker;
	private JComboBox<OptionItem> mMovabilityModelPicker;
	
	private ButtonGroup mNodeTypeButtonGroup;
	
	private JRadioButton mObjectNodeRadioButton;
	private JRadioButton mObstacleNodeRadioButton;
	private JRadioButton mPathNodeRadioButton;
	
	private JCheckBox mAssistanceCheckBox;
	private JCheckBox mAnimationCheckBox;
	
	private JSlider mAnimationSpeedSlider;
	
	private JButton mRunAlgorithmButton;
	private JButton mResetAlgorithmButton;
	private JButton mLoadGraphButton;
	private JButton mSaveGraphButton;
	private JButton mResetGraphButton;
	private JButton mQuitButton;
	
	private boolean mIsInitialized;
	private boolean mIsMovabilityPickerVisible;
	private boolean mIsNodeTypePickerVisibile;
	private boolean mIsAssistanceToggleVisible;
	private boolean mIsAnimationToggleVisible;
	private boolean mIsAnimationSpeedSliderVisible;
	private boolean mIsAssistanceEnabled;
	private boolean mIsAnimationEnabled;
	
	private Callback mCallback;
	
	
	
	
	public static ControlPanelView init(String title, Dimension size, JFrame parent) {
		ControlPanelView controlPanelView = new ControlPanelView(title, size, parent);
		controlPanelView.init();
		
		return controlPanelView;
	}
	
	
	
	
	public ControlPanelView() {
		this("", null, null);
	}
	
	
	
	
	public ControlPanelView(JFrame parent) {
		this("", null, parent);
	}
	
	
	
	
	public ControlPanelView(String title, Dimension size) {
		this(title, size, null);
	}
	
	
	
	
	public ControlPanelView(String title, Dimension size, JFrame parent) {
		mDisplayTitle = title;
		mDisplaySize = size;
		mParent = parent;
		mIsInitialized = false;
	}
	
	
	
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initFrame();
			}
		});
	}
	
	
	
	
	private void initFrame() {
		setTitle(mDisplayTitle);
		setSize(mDisplaySize);
		setMinimumSize(mDisplaySize);
		setPreferredSize(mDisplaySize);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setModalityType(ModalityType.MODELESS);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(mParent);
		setContentPane(createContentPane());
		setVisible(true);
		
		mIsInitialized = true;
	}
	
	
	
	
	
	private JPanel createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		//Preparing the Content Wrapper
		JPanel contentWrapper = new JPanel();
		contentWrapper.setLayout(new GridBagLayout());
		
		//Algorithms Related
		mAlgorithmsLabel = new JLabel("Algorithm:");
		mAlgorithmsLabel.setToolTipText("Algorithm");
		mAlgorithmsLabel.setVerticalAlignment(SwingUtilities.CENTER);
		
		//preparing the Layout Params
		LayoutConstraints constraints = LayoutConstraints.init(1, 1)
				.setGridX(0)
				.setGridY(0)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(10, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mAlgorithmsLabel, 
			constraints.pack()
		);
		
		//Populating the Algorithm Picker and assigning all the necessary listeners
		mAlgorithmPicker = new JComboBox<OptionItem>();
		mAlgorithmPicker.addItem(new OptionItem(Algorithm.DIJKSTRA.name, Algorithm.DIJKSTRA));
		mAlgorithmPicker.addItem(new OptionItem(Algorithm.A_STAR.name, Algorithm.A_STAR));
		mAlgorithmPicker.addItemListener(mAlgorithmPickerListener);
		selectAlgorithm(mSelectedAlgorithm);
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(1, 1)
				.setGridX(1)
				.setGridY(0)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(10, 0, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mAlgorithmPicker, 
			constraints.pack()
		);
		
		//Graph Type Related
		mGraphTypeLabel = new JLabel("Graph Type:");
		mGraphTypeLabel.setToolTipText("Graph Type");
		mGraphTypeLabel.setVerticalAlignment(SwingUtilities.CENTER);
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(1, 1)
				.setGridX(0)
				.setGridY(1)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(0, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mGraphTypeLabel, 
			constraints.pack()
		);
		
		//Populating the Graph Type Picker and assigning all the necessary listeners
		mGraphTypePicker = new JComboBox<OptionItem>();
		mGraphTypePicker.addItem(new OptionItem(GraphType.REGULAR.name, GraphType.REGULAR));
		mGraphTypePicker.addItem(new OptionItem(GraphType.VERTEX_GRID.name, GraphType.VERTEX_GRID));
		mGraphTypePicker.addItemListener(mGraphTypePickerListener);
		selectGraphType(mSelectedGraphType);
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(1, 1)
				.setGridX(1)
				.setGridY(1)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(0, 0, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mGraphTypePicker, 
			constraints.pack()
		);
		
		//Movability Mode Related
		mMovabilityModelLabel = new JLabel("Movability Model:");
		mMovabilityModelLabel.setToolTipText("Movability Model");
		mMovabilityModelLabel.setVerticalAlignment(SwingUtilities.CENTER);
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(1, 1)
				.setGridX(0)
				.setGridY(2)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(0, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mMovabilityModelLabel, 
			constraints.pack()
		);
		
		//Populating the Movability Mode Picker and assigning all the necessary listeners
		mMovabilityModelPicker = new JComboBox<OptionItem>();
		mMovabilityModelPicker.addItem(new OptionItem(MovabilityModel.FOUR_DIRECTIONS_CROSSWISE.name, MovabilityModel.FOUR_DIRECTIONS_CROSSWISE));
		mMovabilityModelPicker.addItem(new OptionItem(MovabilityModel.ANY_DIRECTION.name, MovabilityModel.ANY_DIRECTION));
		mMovabilityModelPicker.addItemListener(mMovabilityModelPickerListener);
		selectMovabilityModel(mSelectedMovabilityModel);
		toggleMovabilityPickerVisibility();
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(1, 1)
				.setGridX(1)
				.setGridY(2)
				.setWeightX(0.5)
				.setWeightY(1)
				.setInsets(new Insets(0, 0, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mMovabilityModelPicker, 
			constraints.pack()
		);
		
		//Node Type Related
		mNodeTypeButtonContainer = new JPanel();
		mNodeTypeButtonContainer.setLayout(createGridLayout(1, 3, 0, 10));
		mNodeTypeButtonContainer.setBorder(getTitledBorder("Node Type"));
		
		mNodeTypeButtonGroup = new ButtonGroup();
		
		mObjectNodeRadioButton = new JRadioButton(Vertex.getNameForVertexType(Vertex.Type.OBJECT));
		mObstacleNodeRadioButton = new JRadioButton(Vertex.getNameForVertexType(Vertex.Type.OBSTACLE));
		mPathNodeRadioButton = new JRadioButton(Vertex.getNameForVertexType(Vertex.Type.PATH));
		
		mObjectNodeRadioButton.setActionCommand(Vertex.getNameForVertexType(Vertex.Type.OBJECT));
		mObstacleNodeRadioButton.setActionCommand(Vertex.getNameForVertexType(Vertex.Type.OBSTACLE));
		mPathNodeRadioButton.setActionCommand(Vertex.getNameForVertexType(Vertex.Type.PATH));
		
		mObjectNodeRadioButton.addItemListener(mNodeTypePickerListener);
		mObstacleNodeRadioButton.addItemListener(mNodeTypePickerListener);
		mPathNodeRadioButton.addItemListener(mNodeTypePickerListener);
		
		mNodeTypeButtonContainer.add(mObjectNodeRadioButton);
		mNodeTypeButtonContainer.add(mObstacleNodeRadioButton);
		mNodeTypeButtonContainer.add(mPathNodeRadioButton);
		
		mNodeTypeButtonGroup.add(mObjectNodeRadioButton);
		mNodeTypeButtonGroup.add(mObstacleNodeRadioButton);
		mNodeTypeButtonGroup.add(mPathNodeRadioButton);
		
		selectNodeType(mSelectedNodeType);
		toggleNodeTypePickerVisibility();
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(3, 1)
				.setGridX(0)
				.setGridY(3)
				.setWeightX(1)
				.setWeightY(1)
				.setInsets(new Insets(20, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mNodeTypeButtonContainer,
			constraints.pack()
		);
		
		//Check Box Related
		mAssistanceCheckBox = new JCheckBox("Straight Line Drawing Assistance");
		mAssistanceCheckBox.addItemListener(mCheckBoxListener);
		toggleAssistanceCheckBoxState();
		toggleAssistanceToggleVisibility();
		
		//preparing the Layout Params
		constraints = LayoutConstraints.init(3, 1)
				.setGridX(0)
				.setGridY(4)
				.setWeightX(1)
				.setWeightY(1)
				.setInsets(new Insets(20, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mAssistanceCheckBox,
			constraints.pack()
		);
		
		mAnimationCheckBox = new JCheckBox("Animate The Whole Process(Slower)");
		mAnimationCheckBox.addItemListener(mCheckBoxListener);
		toggleAnimationCheckBoxState();
		toggleAnimationToggleVisibility();
		
		constraints = LayoutConstraints.init(3, 1)
				.setGridX(0)
				.setGridY(5)
				.setWeightX(1)
				.setWeightY(1)
				.setInsets(new Insets(10, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mAnimationCheckBox,
			constraints.pack()
		);
		
		//Animation Slider Related
		mAnimationSpeedSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		mAnimationSpeedSlider.setMinorTickSpacing(5);
		mAnimationSpeedSlider.setMajorTickSpacing(10);
		mAnimationSpeedSlider.setPaintTicks(true);
		mAnimationSpeedSlider.setPaintLabels(true);
		mAnimationSpeedSlider.setLabelTable(mAnimationSpeedSlider.createStandardLabels(10));
		mAnimationSpeedSlider.addChangeListener(mAnimationSpeedSliderChangeListener);
		toggleAnimationSpeedSliderVisibility();
		
		constraints = LayoutConstraints.init(3, 1)
				.setGridX(0)
				.setGridY(6)
				.setWeightX(1)
				.setWeightY(1)
				.setInsets(new Insets(10, 10, 10, 10))
				.setAnchor(GridBagConstraints.NORTHWEST)
				.setFill(GridBagConstraints.BOTH);
		
		//adding the actual element
		contentWrapper.add(
			mAnimationSpeedSlider,
			constraints.pack()
		);
		
		//Action Button Related
		mActionButtonContainer = new JPanel();
		mActionButtonContainer.setLayout(createGridLayout(6, 1, 10, 10));
		mActionButtonContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		mRunAlgorithmButton = new JButton("Run Algorithm");
		mResetAlgorithmButton = new JButton("Reset Algorithm");
		mLoadGraphButton = new JButton("Load Graph");
		mSaveGraphButton = new JButton("Save Graph");
		mResetGraphButton = new JButton("Reset Graph");
		mQuitButton = new JButton("Quit");
		
		mRunAlgorithmButton.setActionCommand(mRunAlgorithmButton.getText());
		mResetAlgorithmButton.setActionCommand(mResetAlgorithmButton.getText());
		mLoadGraphButton.setActionCommand(mLoadGraphButton.getText());
		mSaveGraphButton.setActionCommand(mSaveGraphButton.getText());
		mResetGraphButton.setActionCommand(mResetGraphButton.getText());
		mQuitButton.setActionCommand(mQuitButton.getText());
		
		mRunAlgorithmButton.addActionListener(mActionButtonActionListener);
		mResetAlgorithmButton.addActionListener(mActionButtonActionListener);
		mLoadGraphButton.addActionListener(mActionButtonActionListener);
		mSaveGraphButton.addActionListener(mActionButtonActionListener);
		mResetGraphButton.addActionListener(mActionButtonActionListener);
		mQuitButton.addActionListener(mActionButtonActionListener);
		
		mActionButtonContainer.add(mRunAlgorithmButton);
		mActionButtonContainer.add(mResetAlgorithmButton);
		mActionButtonContainer.add(mLoadGraphButton);
		mActionButtonContainer.add(mSaveGraphButton);
		mActionButtonContainer.add(mResetGraphButton);
		mActionButtonContainer.add(mQuitButton);
		
		//adding the actual element
		contentPane.add(
			mActionButtonContainer,
			BorderLayout.SOUTH
		);
		
		//adding out actual content to the main pane
		contentPane.add(contentWrapper, BorderLayout.NORTH);
		
		return contentPane;
	}
	
	
	
	
	
	private GridLayout createGridLayout(int rows, int columns, int horizontalPadding, int verticalPadding) {
		GridLayout layout = new GridLayout(rows, columns);
		layout.setHgap(horizontalPadding);
		layout.setVgap(verticalPadding);
		
		return layout;
	}
	
	
	
	
	private File openFilePicker(boolean performExistenceCheck) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Graph Files";
			}
			
			@Override
			public boolean accept(File file) {
				return (file.isDirectory() || file.getName().endsWith(Constants.GRAPH_FILE_EXTENSION));
			}
			
		});
		
		// getting the result code and handling it in the appropriate way
		int resultCode = fileChooser.showOpenDialog(null);
		
		if(resultCode == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			if(!selectedFile.getName().endsWith(Constants.GRAPH_FILE_EXTENSION)) {
				selectedFile = new File(selectedFile.getParentFile(), (selectedFile.getName().replace(".", "").concat(Constants.GRAPH_FILE_EXTENSION)));
			}
			
			return ((performExistenceCheck && !selectedFile.exists()) ? null : selectedFile);
		} else {
			return null;
		}
	}
	
	
	
	
	private TitledBorder getTitledBorder(String title) {
		TitledBorder border = new TitledBorder(title);
		border.setTitleJustification(TitledBorder.CENTER);
		return border;
	}
	
	
	
	
	public void setDisplayTitle(String displayTitle) {
		mDisplayTitle = displayTitle;
	}
	
	
	
	
	public void setDisplaySize(Dimension size) {
		mDisplaySize = size;
	}
	
	
	
	
	public void setDisplaySize(int width, int height) {
		mDisplaySize = new Dimension(width, height);
	}
	
	
	
	
	public void setMovabilityModelPickerVisibility(boolean isVisible) {
		mIsMovabilityPickerVisible = isVisible;
		
		if(mIsInitialized) {
			toggleMovabilityPickerVisibility();
		}
	}
	
	
	
	
	private void toggleMovabilityPickerVisibility() {
		mMovabilityModelLabel.setVisible(mIsMovabilityPickerVisible);
		mMovabilityModelPicker.setVisible(mIsMovabilityPickerVisible);
	}
	
	
	
	
	public void setNodeTypePickerVisibility(boolean isVisible) {
		mIsNodeTypePickerVisibile = isVisible;
		
		if(mIsInitialized) {
			toggleNodeTypePickerVisibility();
		}
	}
	
	
	
	
	private void toggleNodeTypePickerVisibility() {
		mNodeTypeButtonContainer.setVisible(mIsNodeTypePickerVisibile);
	}
	
	
	
	
	public void setAssistanceToggleVisibility(boolean isVisible) {
		mIsAssistanceToggleVisible = isVisible;
		
		if(mIsInitialized) {
			toggleAssistanceToggleVisibility();
		}
	}
	
	
	
	
	private void toggleAssistanceToggleVisibility() {
		mAssistanceCheckBox.setVisible(mIsAssistanceToggleVisible);
	}
	
	
	
	
	public void setAnimationToggleVisibility(boolean isVisible) {
		mIsAnimationToggleVisible = isVisible;
		
		if(mIsInitialized) {
			toggleAnimationToggleVisibility();
		}
	}
	
	
	
	
	private void toggleAnimationToggleVisibility() {
		mAnimationCheckBox.setVisible(mIsAnimationToggleVisible);
	}
	
	
	
	
	public void setAnimationSpeedSliderVisibility(boolean isVisible) {
		mIsAnimationSpeedSliderVisible = isVisible;
		
		if(mIsInitialized) {
			toggleAnimationSpeedSliderVisibility();
		}
	}
	
	
	
	
	private void toggleAnimationSpeedSliderVisibility() {
		mAnimationSpeedSlider.setVisible(mIsAnimationSpeedSliderVisible);
	}
	
	
	
	
	public void setSelectedAlgorithm(Algorithm algorithm) {
		mSelectedAlgorithm = algorithm;
		
		if(mIsInitialized) {
			selectAlgorithm(algorithm);
		}
	}
	
	
	
	
	private void selectAlgorithm(Algorithm algorithm) {
		int itemCount = mAlgorithmPicker.getItemCount();
		int foundIndex = 0;
		
		for(int i = 0; i < itemCount; i++) {
			if(((OptionItem) mAlgorithmPicker.getItemAt(i)).getTag().equals(algorithm)) {
				foundIndex = i;
				break;
			}
		}
		
		mAlgorithmPicker.setSelectedIndex(foundIndex);
	}
	
	
	
	
	public void setSelectedGraphType(GraphType graphType) {
		mSelectedGraphType = graphType;
		
		if(mIsInitialized) {
			selectGraphType(graphType);
		}
	}
	
	
	
	
	private void selectGraphType(GraphType graphType) {
		int itemCount = mGraphTypePicker.getItemCount();
		int foundIndex = 0;
		
		for(int i = 0; i < itemCount; i++) {
			if(((OptionItem) mGraphTypePicker.getItemAt(i)).getTag().equals(graphType)) {
				foundIndex = i;
				break;
			}
		}
		
		mGraphTypePicker.setSelectedIndex(foundIndex);
	}
	
	
	
	
	public void setSelectedMovabilityModel(MovabilityModel movabilityModel) {
		mSelectedMovabilityModel = movabilityModel;
		
		if(mIsInitialized) {
			selectMovabilityModel(movabilityModel);
		}
	}
	
	
	
	
	private void selectMovabilityModel(MovabilityModel movabilityModel) {
		int itemCount = mMovabilityModelPicker.getItemCount();
		int foundIndex = 0;
		
		for(int i = 0; i < itemCount; i++) {
			if(((OptionItem) mMovabilityModelPicker.getItemAt(i)).getTag().equals(movabilityModel)) {
				foundIndex = i;
				break;
			}
		}
		
		mMovabilityModelPicker.setSelectedIndex(foundIndex);
	}
	 
	
	
	
	public void setSelectedNodeType(int nodeType) {
		mSelectedNodeType = nodeType;
		
		if(mIsInitialized) {
			selectNodeType(nodeType);
		}
	}
	
	
	
	
	private void selectNodeType(int nodeType) {
		mObjectNodeRadioButton.setSelected(nodeType == Vertex.Type.OBJECT);
		mObstacleNodeRadioButton.setSelected(nodeType == Vertex.Type.OBSTACLE);
		mPathNodeRadioButton.setSelected(nodeType == Vertex.Type.PATH);
	}
	
	
	
	
	public void setAssistanceEnabled(boolean isEnabled) {
		mIsAssistanceEnabled = isEnabled;
		
		if(mIsInitialized) {
			toggleAssistanceCheckBoxState();
		}
	}
	
	
	
	
	private void toggleAssistanceCheckBoxState() {
		mAssistanceCheckBox.setSelected(mIsAssistanceEnabled);
	}
	
	
	
	
	public void setAnimationEnabled(boolean isEnabled) {
		mIsAnimationEnabled = isEnabled;
		
		if(mIsInitialized) {
			toggleAnimationCheckBoxState();
		}
	}
	
	
	
	
	private void toggleAnimationCheckBoxState() {
		mAnimationCheckBox.setSelected(mIsAnimationEnabled);
	}
	
	
	
	
	public void setCallback(Callback callback) {
		mCallback = callback;
	}
	
	
	
	
	private ItemListener mAlgorithmPickerListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED && mCallback != null) {
				mCallback.onAlgorithmPicked((Algorithm) ((OptionItem) e.getItem()).getTag());
			}
		}
		
	};
	
	
	
	
	private ItemListener mGraphTypePickerListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED && mCallback != null) {
				mCallback.onGraphTypePicked((GraphType) ((OptionItem) e.getItem()).getTag());
			}
		}
		
	};
	
	
	
	
	private ItemListener mMovabilityModelPickerListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED && mCallback != null) {
				mCallback.onMovabilityModelPicked((MovabilityModel) ((OptionItem) e.getItem()).getTag());
			}
		}
		
	};
	
	
	
	
	private ItemListener mNodeTypePickerListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED && mCallback != null) {
				mCallback.onNodeTypePicked(Vertex.getVertexTypeForName(mNodeTypeButtonGroup.getSelection().getActionCommand()));
			}
		}
		
	};
	
	
	
	
	private ItemListener mCheckBoxListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(mCallback == null) {
				return;
			}
			
			if(e.getSource().equals(mAssistanceCheckBox)) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					mCallback.onAssistanceEnabled();
				} else {
					mCallback.onAssistanceDisabled();
				}
			} else if(e.getSource().equals(mAnimationCheckBox)) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					mCallback.onAnimationEnabled();
				} else {
					mCallback.onAnimationDisabled();
				}
			}
		}
		
	};
	
	
	
	
	private ChangeListener mAnimationSpeedSliderChangeListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(mCallback != null) {
				mCallback.onAnimationSpeedRateChanged(mAnimationSpeedSlider.getValue() * 1f / mAnimationSpeedSlider.getMaximum());
			}
		}
		
	};
	
	
	
	
	private ActionListener mActionButtonActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(mCallback == null) {
				return;
			}
			
			String actionCommand = e.getActionCommand();
			
			if(actionCommand.equals(mLoadGraphButton.getActionCommand())) {
				mCallback.onLoadGraph(openFilePicker(true));
			} else if(actionCommand.equals(mSaveGraphButton.getActionCommand())) {
				mCallback.onSaveGraph(openFilePicker(false));
			} else if(actionCommand.equals(mResetGraphButton.getActionCommand())) {
				mCallback.onResetGraph();
			} else if(actionCommand.equals(mResetAlgorithmButton.getActionCommand())) {
				mCallback.onResetAlgorithmButtonClicked();
			} else if(actionCommand.equals(mRunAlgorithmButton.getActionCommand())) {
				mCallback.onRunAlgorithmButtonClicked();
			} else if(actionCommand.equals(mQuitButton.getActionCommand())) {
				mCallback.onQuit();
			}
		}
		
	};
	
	
	
	
	public interface Callback {
		
		public void onAlgorithmPicked(Algorithm algorithm);
		
		public void onGraphTypePicked(GraphType graphType);
		
		public void onMovabilityModelPicked(MovabilityModel movabilityModel);
		
		public void onNodeTypePicked(int nodeType);
		
		public void onAssistanceEnabled();
		
		public void onAssistanceDisabled();
		
		public void onAnimationEnabled();
		
		public void onAnimationDisabled();
		
		public void onAnimationSpeedRateChanged(float speedRate);
		
		public void onRunAlgorithmButtonClicked();
		
		public void onResetAlgorithmButtonClicked();
		
		public void onLoadGraph(File file);
		
		public void onSaveGraph(File file);
		
		public void onResetGraph();
		
		public void onQuit();
		
	}
	
	
	
	

}
