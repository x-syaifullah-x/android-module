# Module View Binding

Example in activity
-
```java
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding { it -> ActivityMainBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
```

Example in fragment
-
```java
// initialization in constructor
class MyFragment : Fragment(R.layout.fragment_your) {

    private val binding by viewBinding<FragmentYourBinding>()
}

class MyFragment : Fragment(R.layout.fragment_your) {

    private val binding by FragmentViewBinding(FragmentYourBinding::class.java)
}

// initialization in onCreateView
class MyFragment : Fragment() {

    private val binding by viewBinding<FragmentYourBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

class MyFragment : Fragment() {

    private val binding by FragmentViewBinding(FragmentYourBinding::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

// your change container
class MyFragment : Fragment() {

    private val binding by viewBinding<FragmentYourBinding>() {
        containerId = R.id.container
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

class MyFragment : Fragment() {

    private val binding by FragmentViewBinding(
        bindingClass = FragmentYourBinding::class.java,
        containerId = R.id.container
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

// your change container and attachToParent
class MyFragment : Fragment() {

    private val binding by viewBinding<FragmentYourBinding>(
        containerId = R.id.container,
        attachToParent = true
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

class MyFragment : Fragment() {

    private val binding1 by FragmentViewBinding(
        bindingClass = FragmentYourBinding::class.java,
        containerId = R.id.container,
        attachToParent = true
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}

// your call binding in onCreate please override onCreateView and return binding.root
class MyFragment : Fragment() {

    private val binding by viewBinding<FragmentYourBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "${binding.root}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root
}
```

---
Publish To Maven Local
-
```bash
./gradlew :viewbinding:publishToMavenLocal
```
---
Dependencies
-
```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:viewbinding:$vModule"
}
```
---
Proguard
-
using data binding add this in proguard if minifyEnabled true
```proguard
-keepclassmembers class ${PACKAGE_NAME}.databinding.** {*;}
```
---