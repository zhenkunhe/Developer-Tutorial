import SpriteKit

class GameScene: SKScene {
    override func didMoveToView(view: SKView) {
        self.backgroundColor = UIColor.lightGrayColor()
        
        var iCon:SKSpriteNode = SKSpriteNode(imageNamed: "aa")
        iCon.size = CGSizeMake(100, 100)
        iCon.anchorPoint = CGPointMake(1, 1)
        iCon.position = CGPointMake(CGRectGetMidX(self.view!.frame), CGRectGetMidY(self.view!.frame))
        self.addChild(iCon)
        
        //變色圖片
        var iCon2:SKSpriteNode = SKSpriteNode(imageNamed: "aa")
        iCon2.size = CGSizeMake(100, 100)
        iCon2.anchorPoint = CGPointMake(0, 1)
        iCon2.color = UIColor.blueColor()
        iCon2.colorBlendFactor = 0.8
        iCon2.position = CGPointMake(CGRectGetMidX(self.view!.frame), CGRectGetMidY(self.view!.frame))
        self.addChild(iCon2)
        
        //連續變色圖片
        var iCon3:SKSpriteNode = SKSpriteNode(imageNamed: "aa")
        iCon3.size = CGSizeMake(100, 100)
        iCon3.position = CGPointMake(CGRectGetMidX(self.view!.frame) + 100, CGRectGetMidY(self.view!.frame) + 30)
        var pluseColor:SKAction = SKAction.sequence([
                SKAction.colorizeWithColor(UIColor.greenColor(), colorBlendFactor: 0.7, duration: 1.0),
                SKAction.colorizeWithColor(UIColor.redColor(), colorBlendFactor: 0.7, duration: 1.0),
                SKAction.colorizeWithColor(UIColor.grayColor(), colorBlendFactor: 0.7, duration: 1.0),
                SKAction.colorizeWithColor(UIColor.clearColor(), colorBlendFactor: 0.7, duration: 1.0)])
        iCon3.runAction(SKAction.repeatActionForever(pluseColor))
        self.addChild(iCon3)
        
        //上方圖片
        var iconText:SKTexture = SKTexture(imageNamed: "aa")
        var count:Int = 5
        var w:CGFloat = self.frame.size.width / CGFloat(count)
        
        var iConTemp:SKSpriteNode!
        for var i = 0 ; i < count ; i++ {
            iConTemp = SKSpriteNode(texture: iconText)
            iConTemp.size = CGSizeMake(w, w)
            iConTemp.position = CGPointMake(w * CGFloat(i) + w / 2, self.frame.size.height - w / 2)
            self.addChild(iConTemp)
        }
        
        var iConAtlas:SKTextureAtlas = SKTextureAtlas(named: "iCons.atlas")
        
        var t_1:SKTexture = iConAtlas.textureNamed("aa")
        var t_2:SKTexture = iConAtlas.textureNamed("bb")
        var t_3:SKTexture = iConAtlas.textureNamed("cc")
        var iConTextures:Array = [t_1,t_2,t_3]
        
        var iConPlayer:SKSpriteNode = SKSpriteNode(color: UIColor.clearColor(), size: CGSizeMake(100, 100))
        iConPlayer.position = CGPointMake(iConPlayer.frame.size.width / 2 , iConPlayer.frame.size.height / 2)
        iConPlayer.runAction(SKAction.repeatActionForever(SKAction.animateWithTextures(iConTextures, timePerFrame: 0.3)))
        self.addChild(iConPlayer)
    }
}

