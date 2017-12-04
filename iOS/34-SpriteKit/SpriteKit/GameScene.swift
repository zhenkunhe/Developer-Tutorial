import SpriteKit

class GameScene: SKScene , SKPhysicsContactDelegate{
    var contentCreated:Bool = false
    let projectileCategory:UInt32 = 0x1 << 0
    let monsterCategory:UInt32 = 0x1 << 1
    //MARK: - Override
    //-------------------------------
    
    override func didMoveToView(view: SKView) {
        if self.contentCreated{
            return
        }
        createSceneContents()
    }
    
    override func update(currentTime: NSTimeInterval) {
        if arc4random() % 4 != 0 {
            return
        }
        self.addRock()
    }
    //用來判斷拔除不該在畫面的東西
    override func didSimulatePhysics() {
        self.enumerateChildNodesWithName("rock", usingBlock: {
            (node:SKNode!,stop: UnsafeMutablePointer<ObjCBool>) -> Void in
            if node.position.y < 0 {
                node.removeFromParent()
            }
        })
    }
    
    //MARK: - Private Create Function
    //-------------------------------
    
    func createSceneContents(){
        self.backgroundColor = UIColor.blackColor()
        self.scaleMode = SKSceneScaleMode.AspectFill
        self.physicsWorld.contactDelegate = self
        
        var ship = newSpaceship()
        ship.position = CGPointMake(frame.size.width / 2, 100)
        ship.physicsBody = SKPhysicsBody(rectangleOfSize: ship.size)
        ship.physicsBody!.affectedByGravity = false
        ship.physicsBody?.categoryBitMask = monsterCategory
        ship.physicsBody?.contactTestBitMask = projectileCategory
        ship.physicsBody?.collisionBitMask = 0
        
        var light = newLight()
        light.position = CGPointMake(0, 0)
        ship.addChild(light)
        
        var light2 = newLight()
        light2.position = CGPointMake(28, 0)
        ship.addChild(light2)
        
        var light3 = newLight()
        light3.position = CGPointMake(-28, 0)
        ship.addChild(light3)
        
        var lightR = newLight()
        lightR.position = CGPointMake(36, 20)
        var around:SKAction = SKAction.sequence([SKAction.moveByX(-(ship.size.width + lightR.size.width), y: 0, duration: 1),SKAction.moveByX(0, y: -(ship.size.height + lightR.size.height), duration: 0.5),SKAction.moveByX(ship.size.width + lightR.size.width, y: 0, duration: 1),SKAction.moveByX(0, y: ship.size.height + lightR.size.height, duration: 0.5)])
        lightR.runAction(SKAction.repeatActionForever(around))
        ship.addChild(lightR)
        
        self.addChild(ship)
    }
    
    func newSpaceship() -> SKSpriteNode{
        var hull:SKSpriteNode = SKSpriteNode(color: UIColor.grayColor(), size: CGSizeMake(64, 32))
        hull.name = "hull"
        var hover:SKAction = SKAction.sequence([SKAction.waitForDuration(1.0),SKAction.moveByX(100, y: 50, duration: 1.0),SKAction.waitForDuration(1.0),SKAction.moveByX(-100, y: -50, duration: 1.0)])
        hull.runAction(SKAction.repeatActionForever(hover))
        return hull
    }
    
    func newLight() -> SKSpriteNode{
        var light:SKSpriteNode = SKSpriteNode(color: UIColor.yellowColor(), size: CGSizeMake(8, 8))
        light.name = "light"
        var blink:SKAction = SKAction.sequence([SKAction.fadeOutWithDuration(0.25),SKAction.fadeInWithDuration(0.25)])
        light.runAction(SKAction.repeatActionForever(blink))
        return light
    }
    
    //產生0~1的亂數浮點數
    func skRandf() -> CGFloat{
        return CGFloat(rand()) / CGFloat(RAND_MAX)
    }
    
    //產生low~high的亂數浮點數
    func skRand(low:CGFloat , high:CGFloat) -> CGFloat{
        return skRandf() * (high - low) + low
    }
    
    func addRock() {
        var rock:SKSpriteNode = SKSpriteNode(color: UIColor.brownColor(), size: CGSizeMake(8, 8))
        rock.name = "rock"
        rock.position = CGPointMake(skRand(0,high: self.frame.size.width), self.frame.size.height - 50)
        rock.physicsBody = SKPhysicsBody(rectangleOfSize: rock.size)
        rock.physicsBody?.categoryBitMask = projectileCategory
        //rock.physicsBody?.contactTestBitMask = monsterCategory
        self.addChild(rock)
    }
    
    override func touchesMoved(touches: NSSet, withEvent event: UIEvent) {
        var touch:UITouch = touches.allObjects[0] as UITouch
        var location:CGPoint = touch.locationInNode(self)
        self.enumerateChildNodesWithName("hull", usingBlock: {
            (node:SKNode!,stop: UnsafeMutablePointer<ObjCBool>) -> Void in
                node.position = location
        })
    }
    
    func didBeginContact(contact: SKPhysicsContact) {
        //將category小的當firstBody,大的當secondBody
        var firstBody:SKPhysicsBody , secondBody:SKPhysicsBody
        if contact.bodyA.categoryBitMask < contact.bodyB.categoryBitMask{
            firstBody = contact.bodyA
            secondBody = contact.bodyB
        }else{
            firstBody = contact.bodyB
            secondBody = contact.bodyA
        }
        
        //category小的是飛彈,大的是石頭
        if ((firstBody.categoryBitMask & projectileCategory) != 0 && (secondBody.categoryBitMask & monsterCategory) != 0) {
            println("hit")
        }
    }
    

}
