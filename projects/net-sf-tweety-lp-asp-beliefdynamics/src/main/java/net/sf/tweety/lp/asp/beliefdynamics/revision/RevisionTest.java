package net.sf.tweety.lp.asp.beliefdynamics.revision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import net.sf.tweety.Formula;
import net.sf.tweety.beliefdynamics.gui.RevisionCompareModel;
import net.sf.tweety.beliefdynamics.gui.RevisionComparePresenter;
import net.sf.tweety.beliefdynamics.gui.RevisionComparePresenter.FileHandler;
import net.sf.tweety.beliefdynamics.gui.RevisionCompareView;
import net.sf.tweety.lp.asp.parser.ASPParser;
import net.sf.tweety.lp.asp.parser.InstantiateVisitor;
import net.sf.tweety.lp.asp.parser.ParseException;
import net.sf.tweety.lp.asp.solver.DLVComplex;
import net.sf.tweety.lp.asp.syntax.Program;

/**
 * Uses the RevisionCompareView in a JFrame to compare the different revision methods in ASP.
 * The first program argument can be used to point to the dlv-complex exectuable. The second
 * program argument can be used to set the current directory of the open belief bases file
 * dialog.
 * @author Tim Janus
 */
public class RevisionTest {
	public static void main(final String [] args) {
		RevisionCompareModel model = new RevisionCompareModel();
		RevisionCompareView view = new RevisionCompareView();
		RevisionComparePresenter presenter = new RevisionComparePresenter(model, view);
		
		presenter.setFileHandler(new FileHandler() {
			
			@Override
			public Collection<? extends Formula> load(File file) {
				if(file == null)
					return null;
				try {
					ASPParser parser = new ASPParser(new FileInputStream(file));
					InstantiateVisitor visitor = new InstantiateVisitor();
					return (Program)parser.Program().jjtAccept(visitor, null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public FileFilter getFilter() {
				return new FileFilter() {
					
					@Override
					public String getDescription() {
						return "*.(asp|dl|dlv)";
					}
					
					@Override
					public boolean accept(File f) {
						String path = f.getPath();
						return f.isDirectory() || path.endsWith("asp") || path.endsWith("dl") || path.endsWith("dlv");
					}
				};
			}

			@Override
			public File getCurrentDiretory() {
				return args.length >= 2 ? new File(args[1]) : new File("."); 
			}
		});
		
		JFrame frame = new JFrame("ASP - Revision Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(view);
		frame.setVisible(true);
		frame.pack();
		
		String path = "";
		String msg = "";
		
		if(args.length >= 1) {
			path = args[0];
		} else {
			msg += "You can set the first program argument to point to your dlv-complex binary. Now you have to use the file-chooser.";
		}
		if(args.length < 2) {
			if(!msg.isEmpty()) msg += "\n";
			msg += "You can set the second program argument to a path which is used as current directory by the load belief base dialog.";
		}
		if(!msg.isEmpty()) {
			JOptionPane.showMessageDialog(view, msg);
		}
		
		if(!new File(path).exists()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			chooser.showOpenDialog(frame);
			path = chooser.getSelectedFile().getPath();
		}
		
		DLVComplex solver = new DLVComplex(path);
		model.addOperator(new PreferenceHandling(solver));
		model.addOperator(new CredibilityRevision(solver));
		
		frame.pack();
	}
}
