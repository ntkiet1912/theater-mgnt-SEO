# Toast Notification Component

A beautiful, customizable toast notification component to replace native `alert()` dialogs.

## Features

✨ **4 Types of Notifications**

- ✅ Success (Green)
- ❌ Error (Red)
- ⚠️ Warning (Yellow)
- ℹ️ Info (Blue)

🎨 **Beautiful Design**

- Smooth animations
- Auto-dismiss after 3 seconds (customizable)
- Manual close button
- Icon indicators
- Responsive layout

## Usage

### 1. Import the hook

```tsx
import { useToast } from "@/hooks/useToast";
import { Toast } from "@/components/ui/Toast";
```

### 2. Initialize in your component

```tsx
export function YourComponent() {
  const { toast, success, error, warning, info, hideToast } = useToast();

  // ... your component logic
}
```

### 3. Show notifications

```tsx
// Success notification
const handleSuccess = () => {
  success("Profile updated successfully!");
};

// Error notification
const handleError = () => {
  error("Failed to update profile. Please try again.");
};

// Warning notification
const handleWarning = () => {
  warning("This action cannot be undone!");
};

// Info notification
const handleInfo = () => {
  info("Please check your email for verification.");
};
```

### 4. Add Toast component to your JSX

```tsx
return (
  <div>
    {/* Your component content */}

    <Toast
      message={toast.message}
      type={toast.type}
      isVisible={toast.isVisible}
      onClose={hideToast}
      duration={3000} // Optional: default is 3000ms
    />
  </div>
);
```

## Complete Example

```tsx
import { useState } from "react";
import { useToast } from "@/hooks/useToast";
import { Toast } from "@/components/ui/Toast";

export function MyForm() {
  const { toast, success, error, hideToast } = useToast();
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async () => {
    try {
      setIsLoading(true);
      await submitForm();
      success("Form submitted successfully!");
    } catch (err) {
      error("Failed to submit form. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        {/* Form fields */}
        <button type="submit">Submit</button>
      </form>

      <Toast
        message={toast.message}
        type={toast.type}
        isVisible={toast.isVisible}
        onClose={hideToast}
      />
    </div>
  );
}
```

## API Reference

### useToast Hook

Returns an object with:

| Property    | Type                                         | Description                                    |
| ----------- | -------------------------------------------- | ---------------------------------------------- |
| `toast`     | `ToastState`                                 | Current toast state (message, type, isVisible) |
| `showToast` | `(message: string, type: ToastType) => void` | Generic show toast function                    |
| `success`   | `(message: string) => void`                  | Show success toast                             |
| `error`     | `(message: string) => void`                  | Show error toast                               |
| `warning`   | `(message: string) => void`                  | Show warning toast                             |
| `info`      | `(message: string) => void`                  | Show info toast                                |
| `hideToast` | `() => void`                                 | Manually hide toast                            |

### Toast Component Props

| Prop        | Type                                          | Default  | Description                                       |
| ----------- | --------------------------------------------- | -------- | ------------------------------------------------- |
| `message`   | `string`                                      | -        | The notification message                          |
| `type`      | `"success" \| "error" \| "warning" \| "info"` | `"info"` | Toast type                                        |
| `duration`  | `number`                                      | `3000`   | Auto-dismiss duration in ms (0 = no auto-dismiss) |
| `isVisible` | `boolean`                                     | -        | Controls visibility                               |
| `onClose`   | `() => void`                                  | -        | Callback when toast is closed                     |

## Replacing alert()

### Before (Old way ❌)

```tsx
alert("Profile updated successfully!");
alert("Failed to update profile");
```

### After (New way ✅)

```tsx
success("Profile updated successfully!");
error("Failed to update profile");
```

## Customization

You can customize colors in `Toast.tsx`:

```tsx
const toastConfig = {
  success: {
    bgColor: "bg-green-500", // Change this
    // ...
  },
  // ...
};
```

## Tips

- Use `success()` for successful operations
- Use `error()` for failed operations
- Use `warning()` for cautionary messages
- Use `info()` for informational messages
- Set `duration={0}` for toasts that don't auto-dismiss
- Multiple toasts will stack if shown rapidly

## Browser Compatibility

✅ Modern browsers (Chrome, Firefox, Safari, Edge)
✅ Mobile browsers
✅ Works with React 18+
