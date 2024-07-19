import { NextResponse } from 'next/server'

export async function GET(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const data = await res.json()
    return NextResponse.json({ data }, { status: 200 })
}

const fields = [
    { name: 'title', message: 'title should not be empty' },
    { name: 'description', message: 'description should not be empty' },
    { name: 'activityLocation', message: 'address should not be empty' },
    { name: 'activityDate', message: 'activityDate should not be empty' },
    { name: 'activityTime', message: 'activityTime should not be empty' }
]

export async function PATCH(req: Request, { params: { id } }: { params: { id: string } }) {
    const formdata = await req.formData()
    const messages = []

    for (const field of fields) {
        const value = formdata.get(field.name)
        if (!value) {
            messages.push(field.message)
        }
    }

    if (messages.length) {
        return NextResponse.json({ message: messages }, { status: 400 })
    }

    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'PATCH',
        headers: {
            authorization: req.headers.get('authorization') || ''
        },
        body: formdata
    })

    if (!res.ok) {
        const { error, status } = await res.json()
        return NextResponse.json({ message: ['failed'], error }, { status })
    }

    return NextResponse.json({ message: 'success' }, { status: 200 })
}

export async function DELETE(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'DELETE',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({}, { status: 200 })
}
