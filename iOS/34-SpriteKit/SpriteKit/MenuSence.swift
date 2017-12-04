import SpriteKit

class MenuSence: SKScene {
    var contentCreated:Bool = false
    
    override func didMoveToView(view: SKView) {
        if self.contentCreated{
            return
        }
        createSceneContents()
    }
    
    //MARK: - Private Create Function
    //-------------------------------
    
    func createSceneContents(){
        self.backgroundColor = UIColor.blueColor()
        self.scaleMode = SKSceneScaleMode.AspectFill
        self.addChild(newHelloNode())
    }
    
    func newHelloNode() -> SKLabelNode{
        let myLabel:SKLabelNode = SKLabelNode(fontNamed:"Chalkduster")
        myLabel.text = "Tap to start!";
        myLabel.name = "StartTag"
        myLabel.fontSize = 42;
        myLabel.position = CGPoint(x:CGRectGetMidX(self.frame), y:CGRectGetMidY(self.frame));
        return myLabel
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
        var helloNode:SKNode? = self.childNodeWithName("StartTag")
        if helloNode != nil {
            helloNode!.name = nil
            var moveUp:SKAction = SKAction.moveToY(100, duration: 0.5)
            var zoom:SKAction = SKAction.scaleTo(2, duration: 0.25)
            var pause:SKAction = SKAction.waitForDuration(0.1)
            var remove:SKAction = SKAction.removeFromParent()
            var moveSequence:SKAction = SKAction.sequence([moveUp,zoom,pause,remove])
            helloNode!.runAction(moveSequence, completion: actionComp)
        }
    }
    
    //MARK: - Completion
    //-------------------------------
    func actionComp(){
        var reveal:SKTransition = SKTransition.flipHorizontalWithDuration(1)
        var myScene:GameScene = GameScene(size: self.scene!.size)
        self.view!.presentScene(myScene, transition: reveal)
    }
}
