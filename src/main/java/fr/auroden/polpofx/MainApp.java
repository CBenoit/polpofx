/*
PolpoFX - Copyright (c) CORTIER Benoît

This software is provided 'as-is', without any express or implied warranty.
In no event will the authors be held liable for any damages arising from
the use of this software.

Permission is granted to anyone to use this software for any purpose,
including commercial applications, and to alter it and redistribute it
freely, subject to the following restrictions:

1. The origin of this software must not be misrepresented; you must not claim
   that you wrote the original software. If you use this software in a product,
   an acknowledgment in the product documentation would be appreciated but is
   not required.

2. Altered source versions must be plainly marked as such, and must not be
   misrepresented as being the original software.

3. This notice may not be removed or altered from any source distribution.
*/

package fr.auroden.polpofx;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {
	public class CustomRotateTransition extends Transition {
		private final double from;
		private final double to;
		private final Node node;

		public CustomRotateTransition(Duration duration, double from, double to, Node node) {
			setCycleDuration(duration);
			setCycleCount(-1);
			setAutoReverse(true);
			this.from = from;
			this.to = to;
			this.node = node;
		}

		protected void interpolate(double frac) {
			// Let's suppose that the first transform is a Rotate transform.
			((Rotate) this.node.getTransforms().get(0)).setAngle(this.from + this.to * frac);
			// Very unsafe I know. I love risky things. :)
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();

		Stop[] stops = new Stop[]{
				new Stop(0.0, Color.rgb(66, 212, 244)),
				new Stop(1.0, Color.rgb(66, 176, 244))
		};
		RadialGradient radialGradient = new RadialGradient(0, 0, 400, 100, 120, false, CycleMethod.REFLECT, stops);

		// tentacles
		List<Polygon> tentacles = new ArrayList<>();

		List<Double[]> polygonDots = new ArrayList<>();
		List<Double[]> rotateInfo = new ArrayList<>();
		polygonDots.add(new Double[]{
				250., 330., // root
				280., 330., // root
				275., 410., // 1
				250., 510., // 2
				200., 580., // tip
				195., 580., // tip
				220., 510., // 2
				245., 410., // 1
		});
		rotateInfo.add(new Double[]{
				265., 330., 20., 80. // pivotX, pivoY, fromAngle, toAngle
		});
		polygonDots.add(new Double[]{
				260., 330., // root
				290., 330., // root
				285., 410., // 1
				260., 510., // 2
				210., 580., // tip
				205., 580., // tip
				230., 510., // 2
				255., 410., // 1
		});
		rotateInfo.add(new Double[]{
				275., 330., 0., 60. // pivotX, pivoY, fromAngle, toAngle
		});
		polygonDots.add(new Double[]{
				270., 330., // root
				300., 330., // root
				295., 410., // 1
				270., 510., // 2
				220., 580., // tip
				215., 580., // tip
				240., 510., // 2
				265., 410., // 1
		});
		rotateInfo.add(new Double[]{
				285., 330., -20., 40. // pivotX, pivoY, fromAngle, toAngle
		});

		polygonDots.add(new Double[]{
				450., 330., // root
				420., 330., // root
				425., 410., // 1
				450., 510., // 2
				500., 580., // tip
				505., 580., // tip
				480., 510., // 2
				455., 410., // 1
		});
		rotateInfo.add(new Double[]{
				435., 330., -20., -80. // pivotX, pivoY, fromAngle, toAngle
		});
		polygonDots.add(new Double[]{
				440., 330., // root
				410., 330., // root
				415., 410., // 1
				440., 510., // 2
				490., 580., // tip
				495., 580., // tip
				470., 510., // 2
				445., 410., // 1
		});
		rotateInfo.add(new Double[]{
				425., 330., 0., -60. // pivotX, pivoY, fromAngle, toAngle
		});
		polygonDots.add(new Double[]{
				430., 330., // root
				400., 330., // root
				405., 410., // 1
				430., 510., // 2
				480., 580., // tip
				485., 580., // tip
				460., 510., // 2
				435., 410., // 1
		});
		rotateInfo.add(new Double[]{
				415., 330., 20., -40. // pivotX, pivoY, fromAngle, toAngle
		});

		assert (rotateInfo.size() == polygonDots.size());

		for (int i = 0; i < polygonDots.size(); i++) {
			Polygon tentacle = new Polygon();
			tentacle.getPoints().addAll(polygonDots.get(i));
			tentacle.getTransforms().add(new Rotate(30, rotateInfo.get(i)[0], rotateInfo.get(i)[1]));
			tentacle.setFill(radialGradient);
			tentacle.setStroke(Color.rgb(66, 176, 244));
			tentacle.setStrokeWidth(5);
			tentacles.add(tentacle);

			CustomRotateTransition customRotateTransition = new CustomRotateTransition(Duration.seconds(3), rotateInfo.get(i)[2], rotateInfo.get(i)[3], tentacle);
			customRotateTransition.play();
		}

		root.getChildren().addAll(tentacles);

		// body
		{
			Ellipse body = new Ellipse(340, 340, 120, 80);
			body.setFill(radialGradient);
			body.setStroke(Color.rgb(66, 176, 244));
			body.setStrokeWidth(5);
			body.setRotate(7);
			root.getChildren().add(body);

			TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), body);
			translateTransition.setFromY(-2);
			translateTransition.setToY(2);
			translateTransition.setCycleCount(-1);
			translateTransition.setAutoReverse(true);
			translateTransition.play();
		}

		// head
		{
			Ellipse head = new Ellipse(350, 200, 150, 165);
			head.setFill(radialGradient);
			head.setStroke(Color.rgb(66, 176, 244));
			head.setStrokeWidth(5);
			head.setRotate(14);
			root.getChildren().add(head);

			TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), head);
			translateTransition.setFromY(5);
			translateTransition.setToY(-5);
			translateTransition.setCycleCount(-1);
			translateTransition.setAutoReverse(true);
			translateTransition.play();
		}

		// eyes
		Ellipse leftEye = new Ellipse(290, 165, 30, 40);
		leftEye.setRotate(18);
		leftEye.setFill(Color.BLACK);
		leftEye.setStroke(Color.WHITE);
		leftEye.setStrokeWidth(30);
		Ellipse rightEye = new Ellipse(400, 175, 30, 40);
		rightEye.setRotate(-3);
		rightEye.setFill(Color.BLACK);
		rightEye.setStroke(Color.WHITE);
		rightEye.setStrokeWidth(30);
		root.getChildren().addAll(leftEye, rightEye);

		// mouth
		Circle mouth = new Circle(330, 250, 50);
		mouth.setFill(Color.BLACK);
		mouth.setStroke(Color.rgb(66, 150, 244));
		mouth.setStrokeWidth(40);
		root.getChildren().add(mouth);

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), mouth);
		scaleTransition.setFromX(1);
		scaleTransition.setFromY(1);
		scaleTransition.setToX(1.1);
		scaleTransition.setToY(1.1);
		scaleTransition.setCycleCount(-1);
		scaleTransition.setAutoReverse(true);
		scaleTransition.play();

		Text text = new Text(150, 550, "Christmas Octopus !");
		text.setFill(Color.DARKCYAN);
		text.setFont(new Font(40));
		root.getChildren().add(text);

		Scene scene = new Scene(root, 700, 600);
		scene.setFill(Color.TRANSPARENT);

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Sono polpo !");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
